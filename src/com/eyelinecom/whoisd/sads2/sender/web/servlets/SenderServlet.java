package com.eyelinecom.whoisd.sads2.sender.web.servlets;

import com.eyelinecom.whoisd.sads2.sender.services.Service;
import com.eyelinecom.whoisd.sads2.sender.services.messaging.FeedbackProvider;
import com.eyelinecom.whoisd.sads2.sender.services.sender.SenderProvider;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author: Artem Voronov
 */
public class SenderServlet extends HttpServlet {

  private final static Logger logger = Logger.getLogger("SADS_SENDER");

  @Inject
  @Service
  private FeedbackProvider feedbackProvider;

  @Inject
  private SenderProvider senderProvider;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    handleRequest(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    handleRequest(req, resp);
  }

  private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      RequestParameters params = new RequestParameters(request);

      if (params.hasSenderMessage()) {
        feedbackProvider.sendAskForTextResponse(response);
      } else {
        senderProvider.initMessageBroadcasting(params.getServiceId(), params.getSenderMessage());
        feedbackProvider.sendMessageWasSent(response);
      }

    } catch(Exception ex) {
      logger.error(ex.getMessage(), ex);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
}
