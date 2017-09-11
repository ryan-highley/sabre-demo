package com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.adapter;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

  @Override
  public String marshal(LocalDate date) throws Exception {
    return date.toString();
  }

  @Override
  public LocalDate unmarshal(String dateString) throws Exception {
    return LocalDate.parse(dateString);
  }

}
