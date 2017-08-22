package com.eyelinecom.whoisd.sads2.sender.services.messaging;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author: Artem Voronov
 */
public interface FeedbackProvider {

  void sendAskForTextResponse(HttpServletResponse response) throws IOException;
  void sendMessageWasSent(HttpServletResponse response) throws IOException;
  void sendCanceled(HttpServletResponse response) throws IOException;
}
