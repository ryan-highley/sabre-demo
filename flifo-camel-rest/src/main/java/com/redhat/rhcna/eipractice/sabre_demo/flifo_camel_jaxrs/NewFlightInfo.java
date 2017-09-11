package com.redhat.rhcna.eipractice.sabre_demo.flifo_camel_jaxrs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.Airport;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "newFlightInfo",
  propOrder = {"destinationAirport", "scheduledDuration"})
public class NewFlightInfo {

  @XmlSchemaType(name = "string")
  protected Airport destinationAirport;
  protected String scheduledDuration;

  public Airport getDestinationAirport() {
    return destinationAirport;
  }

  public void setDestinationAirport(Airport destinationAirport) {
    this.destinationAirport = destinationAirport;
  }

  public String getScheduledDuration() {
    return scheduledDuration;
  }

  public void setScheduledDuration(String scheduledDuration) {
    this.scheduledDuration = scheduledDuration;
  }


}
