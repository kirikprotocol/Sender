package com.eyelinecom.whoisd.sads2.sender.services.messaging;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * author: Artem Voronov
 */
public interface FeedbackProvider {

  void sendAskForTextResponse(HttpServletResponse response, String exitUrl) throws IOException;
  void sendNotifyAllResult(HttpServletResponse response, Map<String, Integer> frequency) throws IOException;
}
