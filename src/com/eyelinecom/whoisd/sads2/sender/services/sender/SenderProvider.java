package com.eyelinecom.whoisd.sads2.sender.services.sender;

import java.util.Map;

/**
 * author: Artem Voronov
 */
public interface SenderProvider {

  Map<String, Integer> initMessageBroadcasting(String serviceId, String message);
}
