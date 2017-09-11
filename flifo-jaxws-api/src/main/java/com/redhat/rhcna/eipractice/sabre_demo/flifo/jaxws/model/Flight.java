package com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Flight {
  private Airline airline;
  private String flightNumber;
  private Airport originAirport;
  private Airport destinationAirport;
  private LocalTime scheduleDepartureTime;
  private Duration scheduledDuration;

  public Airline getAirline() {
    return airline;
  }

  public void setAirline(Airline airline) {
    this.airline = airline;
  }

  public String getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  public Airport getOriginAirport() {
    return originAirport;
  }

  public void setOriginAirport(Airport originAirport) {
    this.originAirport = originAirport;
  }

  public Airport getDestinationAirport() {
    return destinationAirport;
  }

  public void setDestinationAirport(Airport destinationAirport) {
    this.destinationAirport = destinationAirport;
  }

  public LocalTime getScheduleDepartureTime() {
    return scheduleDepartureTime;
  }

  public void setScheduleDepartureTime(LocalTime scheduleDepartureTime) {
    this.scheduleDepartureTime = scheduleDepartureTime;
  }

  public Duration getScheduledDuration() {
    return scheduledDuration;
  }

  public void setScheduledDuration(Duration scheduledDuration) {
    this.scheduledDuration = scheduledDuration;
  }

  public Instant getScheduledDepartureForDate(LocalDate date) {
    if (scheduleDepartureTime == null || originAirport == null)
      throw new IllegalStateException(
        "Cannot calculate scheduled departure date and time for local date without scheduled departure time and/or origin airport");

    return ZonedDateTime.of(date, scheduleDepartureTime, originAirport.getLocalTimeZone())
      .toInstant();
  }

  public Instant getScheduledArrivalForDate(LocalDate date) {
    if (scheduledDuration == null)
      throw new IllegalStateException(
        "Cannot calculate scheduled arrival date and time for local date without scheduled duration");

    return getScheduledDepartureForDate(date).plus(scheduledDuration);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((airline == null) ? 0 : airline.hashCode());
    result = prime * result + ((flightNumber == null) ? 0 : flightNumber.hashCode());
    result = prime * result + ((originAirport == null) ? 0 : originAirport.hashCode());
    result =
      prime * result + ((scheduleDepartureTime == null) ? 0 : scheduleDepartureTime.hashCode());
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
    Flight other = (Flight) obj;
    if (airline != other.airline)
      return false;
    if (flightNumber == null) {
      if (other.flightNumber != null)
        return false;
    } else if (!flightNumber.equals(other.flightNumber))
      return false;
    if (originAirport != other.originAirport)
      return false;
    if (scheduleDepartureTime == null) {
      if (other.scheduleDepartureTime != null)
        return false;
    } else if (!scheduleDepartureTime.equals(other.scheduleDepartureTime))
      return false;
    return true;
  }

}
