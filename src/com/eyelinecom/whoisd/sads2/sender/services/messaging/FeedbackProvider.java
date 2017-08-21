package com.eyelinecom.whoisd.sads2.sender.services.messaging;

import javax.servlet.http.HttpServletResponse;

/**
 * author: Artem Voronov
 */
public interface FeedbackProvider {

  void sendAskForTextResponse(HttpServletResponse response);
  void sendMessageWasSent(HttpServletResponse response);
}
