package com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class FlightInfo {
  private Flight flight;
  private Status status;
  private Instant scheduledDepartureTime;
  private Instant expectedDepartureTime;
  private Duration expectedDuration;
  private Instant actualDepartureTime;
  private Duration actualDuration;
  private Airport actualArrivalAirport;

  public Flight getFlight() {
    return flight;
  }

  public void setFlight(Flight flight) {
    this.flight = flight;
  }

  public Status getStatus() {
    if (actualDepartureTime != null) {
      updateStatusWithInstant(actualDepartureTime);
    } else if (expectedDepartureTime != null) {
      updateStatusWithInstant(expectedDepartureTime);
    } else {
      status = Status.ON_TIME;
    }

    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  private void updateStatusWithInstant(Instant comparisonInstant) {
    if (status != Status.ON_TIME || comparisonInstant == null)
      return;

    Duration scheduledToComparison = Duration.between(scheduledDepartureTime, comparisonInstant);
    if (scheduledToComparison.compareTo(Duration.ofMinutes(15L)) < 0) {
      status = Status.ON_TIME;
    } else {
      status = Status.DELAYED;
    }
  }

  public void cancelFlight() {
    status = Status.CANCELLED;
  }

  public Instant getScheduledDepartureTime() {
    return scheduledDepartureTime;
  }

  public void setScheduledDepartureTime(Instant scheduledDepartureTime) {
    this.scheduledDepartureTime = scheduledDepartureTime;
  }

  public Instant getExpectedDepartureTime() {
    return expectedDepartureTime;
  }

  public void setExpectedDepartureTime(Instant expectedDepartureTime) {
    this.expectedDepartureTime = expectedDepartureTime;
  }

  @XmlTransient
  public Instant getExpectedArrivalTime() {
    if (expectedDepartureTime == null || expectedDuration == null)
      throw new IllegalStateException(
        "Unable to calculate expected arrival time without expected departure time and/or expected duration");

    return expectedDepartureTime.plus(expectedDuration);
  }

  public void setExpectedArrivalTime(Instant expectedArrivalTime) {
    if (expectedDepartureTime == null)
      throw new IllegalStateException(
        "Unable to calculated expected duration without expected departure time");

    this.expectedDuration = Duration.between(expectedDepartureTime, expectedArrivalTime);
  }

  public Duration getExpectedDuration() {
    return expectedDuration;
  }

  public void setExpectedDuration(Duration expectedDuration) {
    this.expectedDuration = expectedDuration;
  }

  public Instant getActualDepartureTime() {
    return actualDepartureTime;
  }

  public void setActualDepartureTime(Instant actualDepartureTime) {
    this.actualDepartureTime = actualDepartureTime;
  }

  @XmlTransient
  public Instant getActualArrivalTime() {
    if (actualDepartureTime == null || actualDuration == null)
      throw new IllegalStateException(
        "Unable to calculate actual arrival time without actual departure time and/or actual duration");

    return actualDepartureTime.plus(actualDuration);
  }

  public void setActualArrivalTime(Instant actualArrivalTime) {
    if (actualDepartureTime == null)
      throw new IllegalStateException(
        "Unable to calculated actual duration without actual departure time");

    this.actualDuration = Duration.between(actualDepartureTime, actualArrivalTime);
  }

  public Duration getActualDuration() {
    return actualDuration;
  }

  public void setActualDuration(Duration actualDuration) {
    this.actualDuration = actualDuration;
  }

  public Airport getActualArrivalAirport() {
    return actualArrivalAirport;
  }

  public void setActualArrivalAirport(Airport arrivalAirport) {
    this.actualArrivalAirport = arrivalAirport;
  }

  public enum Status {
    CANCELLED, DELAYED, ON_TIME, ARRIVED, DIVERTED;
  }

  public static FlightInfo createForDate(Flight flight, LocalDate date) {
    FlightInfo flightInfo = new FlightInfo();

    flightInfo.setFlight(flight);
    flightInfo.setStatus(Status.ON_TIME);

    Instant departureInstant = ZonedDateTime
      .of(date, flight.getScheduleDepartureTime(), flight.getOriginAirport().getLocalTimeZone())
      .toInstant();
    flightInfo.setScheduledDepartureTime(departureInstant);

    flightInfo.setExpectedDepartureTime(departureInstant);
    flightInfo.setExpectedDuration(flight.getScheduledDuration());

    return flightInfo;
  }
}
