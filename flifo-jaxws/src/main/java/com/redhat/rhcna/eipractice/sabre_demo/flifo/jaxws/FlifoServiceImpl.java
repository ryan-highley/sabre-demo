package com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jws.WebService;

import com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.Airline;
import com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.Airport;
import com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.Flight;
import com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.FlightInfo;

@WebService(endpointInterface = "com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.FlifoService",
  serviceName = "FlifoService",
  targetNamespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws")
public class FlifoServiceImpl implements FlifoService {

  private static final Map<FlightInfoKey, FlightInfo> currentFlightInfo = new ConcurrentHashMap<>();

  @Override
  public FlightInfo getFlightInfo(Flight flight, LocalDate originalScheduledDepartureDate) {
    FlightInfo tempFlightInfo = FlightInfo.createForDate(flight, originalScheduledDepartureDate);

    FlightInfo flightInfo = currentFlightInfo.get(new FlightInfoKey(tempFlightInfo));
    
    return flightInfo;
  }

  @Override
  public FlightInfo createFlight(Flight flight, LocalDate date) {
    FlightInfo flightInfo = getFlightInfo(flight, date);
    if (flightInfo == null) {
      FlightInfo newFlightInfo = FlightInfo.createForDate(flight, date);
      FlightInfoKey newFlightInfoKey = new FlightInfoKey(newFlightInfo);
      flightInfo = currentFlightInfo.putIfAbsent(newFlightInfoKey, newFlightInfo);
      if (flightInfo == null) {
        flightInfo = newFlightInfo;
      }
    }
    return flightInfo;
  }

  @Override
  public List<FlightInfo> createFlights(Flight flight, List<LocalDate> dates) {
    List<FlightInfo> flightInfos = new ArrayList<>(dates.size());

    for (LocalDate date : dates) {
      flightInfos.add(createFlight(flight, date));
    }

    return flightInfos;
  }

  private FlightInfo updateFlightInfo(FlightInfo newFlightInfo) {
    FlightInfoKey key = new FlightInfoKey(newFlightInfo);

    boolean success = false;
    do {
      FlightInfo existingFlightInfo = currentFlightInfo.get(key);
      success = currentFlightInfo.replace(key, existingFlightInfo, newFlightInfo);
    } while (!success);

    return newFlightInfo;
  }

  @Override
  public FlightInfo updateExpectedDeparture(Flight flight, LocalDate originalScheduledDepartureDate,
    Instant newExpectedDeparture, Duration newExpectedDuration) {
    FlightInfo flightInfo = getFlightInfo(flight, originalScheduledDepartureDate);
    if (flightInfo == null) {
      flightInfo = createFlight(flight, originalScheduledDepartureDate);
    }

    flightInfo.setExpectedDepartureTime(newExpectedDeparture);
    if (newExpectedDuration != null) {
      flightInfo.setExpectedDuration(newExpectedDuration);
    }

    return updateFlightInfo(flightInfo);
  }

  @Override
  public FlightInfo updateActualDeparture(Flight flight, LocalDate originalScheduledDepartureDate,
    Instant actualDeparture) {
    FlightInfo flightInfo = getFlightInfo(flight, originalScheduledDepartureDate);
    if (flightInfo == null) {
      flightInfo = createFlight(flight, originalScheduledDepartureDate);
    }

    flightInfo.setActualDepartureTime(actualDeparture);

    return updateFlightInfo(flightInfo);
  }

  @Override
  public FlightInfo updateActualArrival(Flight flight, LocalDate originalScheduledDepartureDate,
    Instant actualArrival, Airport actualArrivalAirport) {
    FlightInfo flightInfo = getFlightInfo(flight, originalScheduledDepartureDate);
    if (flightInfo == null) {
      flightInfo = createFlight(flight, originalScheduledDepartureDate);
    }

    flightInfo.setActualArrivalTime(actualArrival);

    flightInfo.setActualArrivalAirport(actualArrivalAirport);

    return updateFlightInfo(flightInfo);
  }

  private static class FlightInfoKey {
    private Airline airline;
    private String flightNumber;
    private Airport originAirport;
    private Instant scheduledDeparture;

    private FlightInfoKey(FlightInfo flightInfo) {
      this.airline = flightInfo.getFlight().getAirline();
      this.flightNumber = flightInfo.getFlight().getFlightNumber();
      this.originAirport = flightInfo.getFlight().getOriginAirport();
      this.scheduledDeparture = flightInfo.getScheduledDepartureTime();
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((airline == null) ? 0 : airline.hashCode());
      result = prime * result + ((flightNumber == null) ? 0 : flightNumber.hashCode());
      result = prime * result + ((originAirport == null) ? 0 : originAirport.hashCode());
      result = prime * result + ((scheduledDeparture == null) ? 0 : scheduledDeparture.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      FlightInfoKey other = (FlightInfoKey) obj;
      if (airline != other.airline)
        return false;
      if (flightNumber == null) {
        if (other.flightNumber != null)
          return false;
      } else if (!flightNumber.equals(other.flightNumber))
        return false;
      if (originAirport != other.originAirport)
        return false;
      if (scheduledDeparture == null) {
        if (other.scheduledDeparture != null)
          return false;
      } else if (!scheduledDeparture.equals(other.scheduledDeparture))
        return false;
      return true;
    }
  }
}
