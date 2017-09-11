package com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.adapter;

import java.time.Duration;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DurationAdapter extends XmlAdapter<String, Duration> {

  @Override
  public String marshal(Duration duration) throws Exception {
    return duration.toString();
  }

  @Override
  public Duration unmarshal(String durationString) throws Exception {
    return Duration.parse(durationString);
  }

}
