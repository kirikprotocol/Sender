package com.eyelinecom.whoisd.sads2.sender.services.sender;

/**
 * author: Artem Voronov
 */
public class SenderService implements SenderProvider {

  public SenderService(String profileStorageApiUrl, String pushApiUrl, int connectTimeoutInMillis, int requestTimeoutInMillis) {

  }

  @Override
  public void initMessageBroadcasting(String serviceId, String message) {
    //TODO
  }
}
