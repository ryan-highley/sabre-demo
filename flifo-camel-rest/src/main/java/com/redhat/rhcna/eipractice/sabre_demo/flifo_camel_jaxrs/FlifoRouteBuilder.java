package com.redhat.rhcna.eipractice.sabre_demo.flifo_camel_jaxrs;

import java.util.Map;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.extension.camel.CamelAware;

import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.Airline;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.Airport;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.CreateFlight;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.FlifoService;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.Flight;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.FlightInfo;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.GetFlightInfo;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.ObjectFactory;

@Startup
@CamelAware
@ApplicationScoped
public class FlifoRouteBuilder extends RouteBuilder {

  private static final Logger LOG = LoggerFactory.getLogger(FlifoRouteBuilder.class);

  @Inject
  private CamelContext camelContext;

  @Inject
  private ObjectFactory objectFactory;

  @Override
  public void configure() throws Exception {

    CxfEndpoint flifoServiceEndpoint = new CxfEndpoint();
    flifoServiceEndpoint.setBeanId("flifoServiceEndpoint");
    flifoServiceEndpoint.setAddress("http://localhost:8080/flifo-jaxws/FlifoService");
    flifoServiceEndpoint.setServiceClass(FlifoService.class);
    flifoServiceEndpoint
      .setDefaultOperationNamespace("urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws");
    flifoServiceEndpoint.setCamelContext(camelContext);

    //@formatter:off
    
    restConfiguration()
      .component("servlet")
      .componentProperty("servletName", "RestCamelServlet")
      .jsonDataFormat("json-jackson")
      ;
    
    rest("/flifo/{airline}/{flightNumber}/{departureAirport}/{departureTime}")
      .produces(MediaType.APPLICATION_JSON)
      .consumes(MediaType.APPLICATION_JSON)
      .get("/{scheduledDate}")
        .outType(FlightInfo.class)
        .to("direct:getFlifo")
      .post("/{scheduledDate}")
        .type(NewFlightInfo.class)
        .outType(FlightInfo.class)
        .to("direct:createFlight")
      ;
    
    from("direct:getFlifo")
      .process(convertHeadersToGetFlightInfo)
      .setHeader(CxfConstants.OPERATION_NAME, constant("getFlightInfo"))
      .to("direct:sendToFlifoService")
      ;
    
    from("direct:createFlight")
      .unmarshal().json(JsonLibrary.Jackson)
      .process(convertHeadersToCreateFlight)
      .setHeader(CxfConstants.OPERATION_NAME, constant("createFlight"))
      .to("direct:sendToFlifoService")
      ;
      
    from("direct:sendToFlifoService")
      .to(flifoServiceEndpoint)
      .transform().simple("${body[0].flightInfo}", FlightInfo.class)
      .toD("log:" + FlifoRouteBuilder.class.getName() + ".${in.header." + CxfConstants.OPERATION_NAME + "}?showAll=true&multiline=true")
      .marshal().json(JsonLibrary.Jackson)
      .setHeader(HttpHeaders.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
      ;

    //@formatter:on
  }

  private Processor convertHeadersToGetFlightInfo = (Exchange exch) -> {
    Flight flight = createFlightFromHeaders(exch);

    GetFlightInfo getFlightInfo = objectFactory.createGetFlightInfo();

    getFlightInfo.setFlight(flight);
    getFlightInfo
      .setOriginalScheduledDepartureDate(exch.getIn().getHeader("scheduledDate", String.class));

    exch.getIn().setBody(getFlightInfo);
  };
  
  private Processor convertHeadersToCreateFlight = (Exchange exch) -> {
    Flight flight = createFlightFromHeaders(exch);
    
    @SuppressWarnings("unchecked")
    Map<String, Object> body = exch.getIn().getBody(Map.class);
    flight.setDestinationAirport(Airport.fromValue(body.get("destinationAirport").toString()));
    flight.setScheduledDuration(body.get("scheduledDuration").toString());
    
//    NewFlightInfo newFlightInfo = exch.getIn().getBody(NewFlightInfo.class);
    
//    flight.setDestinationAirport(newFlightInfo.getDestinationAirport());
//    flight.setScheduledDuration(newFlightInfo.getScheduledDuration());
    
    CreateFlight createFlight = objectFactory.createCreateFlight();
    
    createFlight.setFlight(flight);
    createFlight.setDate(exch.getIn().getHeader("scheduledDate", String.class));

    exch.getIn().setBody(createFlight);
  };

  private Flight createFlightFromHeaders(Exchange exch) {
    Message in = exch.getIn();

    Flight flight = new Flight();

    Airline airline = Airline.fromValue(in.getHeader("airline", String.class));
    String flightNumber = in.getHeader("flightNumber", String.class);
    Airport departureAirport = Airport.fromValue(in.getHeader("departureAirport", String.class));
    String scheduledDepartureTime = in.getHeader("departureTime", String.class);

    flight.setAirline(airline);
    flight.setFlightNumber(flightNumber);
    flight.setOriginAirport(departureAirport);
    flight.setScheduleDepartureTime(scheduledDepartureTime);

    return flight;
  }

}
