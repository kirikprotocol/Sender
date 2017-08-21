package com.eyelinecom.whoisd.sads2.sender.services.sender;

/**
 * author: Artem Voronov
 */
public interface SenderProvider {

  void initMessageBroadcasting(String serviceId, String message);
}
