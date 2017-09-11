package com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.adapter;

import java.time.Instant;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class InstantAdapter extends XmlAdapter<String, Instant> {

  @Override
  public String marshal(Instant instant) throws Exception {
    return instant.toString();
  }

  @Override
  public Instant unmarshal(String instantString) throws Exception {
    return Instant.parse(instantString);
  }

}
