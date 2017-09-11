package com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model;

import java.time.ZoneId;

public enum Airport {
  KDFW(ZoneId.of("America/Chicago")), KOKC(ZoneId.of("America/Chicago")), KJFK(
    ZoneId.of("America/New_York")), KDTW(ZoneId.of("America/New_York")), KLAS(
      ZoneId.of("America/Los_Angeles")), KORD(
        ZoneId.of("America/Chicago")), KMCO(ZoneId.of("America/New_York"));

  private Airport(ZoneId localTimeZone) {
    this.localTimeZone = localTimeZone;
  }

  private final ZoneId localTimeZone;

  public ZoneId getLocalTimeZone() {
    return localTimeZone;
  }
}
