package com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.adapter;

import java.time.ZonedDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {

  @Override
  public String marshal(ZonedDateTime date) throws Exception {
    return date.toString();
  }

  @Override
  public ZonedDateTime unmarshal(String dateString) throws Exception {
    return ZonedDateTime.parse(dateString);
  }

}
