package com.eyelinecom.whoisd.sads2.sender.services.messaging;

import com.eyelinecom.whoisd.sads2.sender.web.servlets.RequestParameters;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * author: Artem Voronov
 */
public interface FeedbackProvider {

  void sendAskForTextResponse(Locale locale, HttpServletResponse response, String exitUrl, String senderOwner) throws IOException;
  void sendNotifyAllResult(Locale locale, HttpServletResponse response, Map<String, Integer> frequency) throws IOException;
  void sendAccessDenied(Locale locale, HttpServletResponse response) throws IOException;
  void sendToMsisdnVerification(HttpServletResponse response, RequestParameters parameters) throws IOException;
}
