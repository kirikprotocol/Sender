package com.eyelinecom.whoisd.sads2.sender;

import com.eyeline.utils.config.xml.XmlConfigSection;
import com.eyelinecom.whoisd.sads2.sender.services.Services;
import com.eyelinecom.whoisd.sads2.sender.services.ServicesException;

import javax.enterprise.context.ApplicationScoped;

/**
 * author: Artem Voronov
 */
@ApplicationScoped
public class WebContext {

  private static Services services;
  private static String storageApiUrl;
  private static String pushApiUrl;


  static synchronized void init(XmlConfigSection config, String pushApiUrl, String storageApiUrl) throws ServicesException {
    if(WebContext.services == null) {
      WebContext.services = new Services(config);
      WebContext.pushApiUrl = pushApiUrl;
      WebContext.storageApiUrl = storageApiUrl;
    }
  }


  public static String getStorageApiUrl() {
    return storageApiUrl;
  }

  public static String getPushApiUrl() {
    return pushApiUrl;
  }
}
