package com.eyelinecom.whoisd.sads2.sender;

import com.eyeline.utils.config.xml.XmlConfigSection;
import com.eyelinecom.whoisd.sads2.sender.services.Service;
import com.eyelinecom.whoisd.sads2.sender.services.Services;
import com.eyelinecom.whoisd.sads2.sender.services.ServicesException;
import com.eyelinecom.whoisd.sads2.sender.services.messaging.FeedbackProvider;
import com.eyelinecom.whoisd.sads2.sender.services.sender.SenderProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * author: Artem Voronov
 */
@ApplicationScoped
public class WebContext {

  private static Services services;

  static synchronized void init(XmlConfigSection config) throws ServicesException {
    if(WebContext.services == null) {
      WebContext.services = new Services(config);
    }
  }

  @Produces
  @Service
  public FeedbackProvider getFeedbackProvider() {
    return services.getFeedbackProvider();
  }

  @Produces
  public SenderProvider getSenderProvider() {
    return services.getSenderProvider();
  }
}
