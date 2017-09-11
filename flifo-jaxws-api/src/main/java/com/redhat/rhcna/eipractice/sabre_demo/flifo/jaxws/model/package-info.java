@XmlJavaTypeAdapters({
  @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class),
  @XmlJavaTypeAdapter(type = ZonedDateTime.class, value = ZonedDateTimeAdapter.class),
  @XmlJavaTypeAdapter(type = LocalTime.class, value = LocalTimeAdapter.class),
  @XmlJavaTypeAdapter(type = Instant.class, value = InstantAdapter.class),
  @XmlJavaTypeAdapter(type = Duration.class, value = DurationAdapter.class),
})
@XmlSchema(namespace = "urn:eipractice.rhcna.redhat.com/sabre_demo/flifo-jaxws", elementFormDefault = XmlNsForm.QUALIFIED)
package com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import javax.xml.bind.annotation.XmlNsForm;

import com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.adapter.DurationAdapter;
import com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.adapter.InstantAdapter;
import com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.adapter.LocalDateAdapter;
import com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.adapter.LocalTimeAdapter;
import com.redhat.rhcna.eipractice.sabre_demo.flifo.jaxws.model.adapter.ZonedDateTimeAdapter;
