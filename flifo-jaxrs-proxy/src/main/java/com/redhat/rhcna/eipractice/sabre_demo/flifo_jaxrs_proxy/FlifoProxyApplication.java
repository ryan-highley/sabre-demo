package com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxrs_proxy;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.FlifoService;
import com.redhat.rhcna.eipractice.sabre_demo.flifo_jaxws.FlifoService_Service;

@ApplicationPath("/")
public class FlifoProxyApplication extends Application {
  private static final Logger LOG = LoggerFactory.getLogger(FlifoProxyApplication.class);
  
  @Inject
  private FlifoService_Service flifoServiceFactory;
  
  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> classes = new HashSet<>();
    classes.addAll(super.getClasses());
    classes.add(FlifoServiceProxy.class);
    return classes;
  }
  
  @Produces
  public FlifoService getFlifoService() {
    LOG.info("Creating FlifoService proxy");
    return flifoServiceFactory.getFlifoServiceImplPort();
  }
}
