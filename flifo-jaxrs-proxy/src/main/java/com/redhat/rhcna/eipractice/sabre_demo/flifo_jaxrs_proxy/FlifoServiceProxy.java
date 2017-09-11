package com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxrs_proxy;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceRef;

import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.CreateFlight;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.CreateFlightResponse;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.CreateFlights;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.CreateFlightsResponse;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.FlifoService;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.FlifoService_Service;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.GetFlightInfo;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.GetFlightInfoResponse;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.UpdateActualArrival;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.UpdateActualArrivalResponse;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.UpdateActualDeparture;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.UpdateActualDepartureResponse;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.UpdateExpectedDeparture;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.UpdateExpectedDepartureResponse;

@ApplicationScoped
@Path("/flifo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FlifoServiceProxy {

  @Inject
  private FlifoService flifoService;

  @Inject
  private FlifoService_Service service;
  
  @POST
  @Path("/getFlightInfo")
  public GetFlightInfoResponse getFlightInfo(GetFlightInfo getFlightInfo) {
    FlifoService proxy = service.getFlifoServiceImplPort();
    return proxy.getFlightInfo(getFlightInfo);
    
//    return flifoService.getFlightInfo(getFlightInfo);
  }
  
  @POST
  @Path("/createFlight")
  public CreateFlightResponse createFlight(CreateFlight createFlight) {
    return flifoService.createFlight(createFlight);
  }
  
  @POST
  @Path("/createFlights")
  public CreateFlightsResponse createFlights(CreateFlights createFlights) {
    return flifoService.createFlights(createFlights);
  }
  
  @POST
  @Path("/updateActualArrival")
  public UpdateActualArrivalResponse updateActualArrival(UpdateActualArrival updateActualArrival) {
    return flifoService.updateActualArrival(updateActualArrival);
  }
  
  @POST
  @Path("/updateActualDeparture")
  public UpdateActualDepartureResponse updateActualDeparture(UpdateActualDeparture updateActualDeparture) {
    return flifoService.updateActualDeparture(updateActualDeparture);
  }
  
  @POST
  @Path("/updateExpectedDeparture")
  public UpdateExpectedDepartureResponse updateExpectedDeparture(UpdateExpectedDeparture updateExpectedDeparture) {
    return flifoService.updateExpectedDeparture(updateExpectedDeparture);
  }
}
