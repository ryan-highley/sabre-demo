package com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.Airport;
import com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.Flight;
import com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.FlightInfo;

@WebService(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws")
public interface FlifoService {

  public @WebResult(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
    name = "flightInfo") FlightInfo createFlight(
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "flight") Flight flight,
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "date") LocalDate date);

  public @WebResult(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
    name = "flightInfoList") List<FlightInfo> createFlights(
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "flight") Flight flight,
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "dates") List<LocalDate> dates);

  public @WebResult(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
    name = "flightInfo") FlightInfo getFlightInfo(
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "flight") Flight flight,
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "originalScheduledDepartureDate") LocalDate originalScheduledDepartureDate);

  public @WebResult(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
    name = "flightInfo") FlightInfo updateExpectedDeparture(
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "flight") Flight flight,
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "originalScheduledDepartureDate") LocalDate originalScheduledDepartureDate,
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "newExpectedDeparture") Instant newExpectedDeparture,
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "newExpectedDuration") Duration newExpectedDuration);

  public @WebResult(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
    name = "flightInfo") FlightInfo updateActualDeparture(
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "flight") Flight flight,
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "originalScheduledDepartureDate") LocalDate originalScheduledDepartureDate,
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "actualDeparture") Instant actualDeparture);

  public @WebResult(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
    name = "flightInfo") FlightInfo updateActualArrival(
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "flight") Flight flight,
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "originalScheduledDepartureDate") LocalDate originalScheduledDepartureDate,
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "actualArrival") Instant actualArrival,
      @WebParam(targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws",
        name = "actualArrivalAirport") Airport actualArrivalAirport);
}
