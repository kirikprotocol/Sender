package com.eyelinecom.whoisd.sads2.sender;

import com.eyeline.utils.config.xml.XmlConfigSection;
import com.eyelinecom.whoisd.sads2.sender.services.Services;
import com.eyelinecom.whoisd.sads2.sender.services.ServicesException;
import com.eyelinecom.whoisd.sads2.sender.services.messaging.FeedbackProvider;
import com.eyelinecom.whoisd.sads2.sender.services.sender.SenderProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

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
  @Named("feedback")
  public FeedbackProvider getFeedbackProvider() {
    return services.getFeedbackProvider();
  }

  @Produces
  @Named("sender")
  public SenderProvider getSenderProvider() {
    return services.getSenderProvider();
  }
}
