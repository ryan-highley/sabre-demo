package com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.adapter;

import java.time.LocalTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {

  @Override
  public String marshal(LocalTime time) throws Exception {
    return time.toString();
  }

  @Override
  public LocalTime unmarshal(String timeString) throws Exception {
    return LocalTime.parse(timeString);
  }

}
