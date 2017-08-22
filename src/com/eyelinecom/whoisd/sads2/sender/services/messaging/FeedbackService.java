package com.eyelinecom.whoisd.sads2.sender.services.messaging;

import com.eyelinecom.whoisd.sads2.sender.utils.Templates;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * author: Artem Voronov
 */
public class FeedbackService implements FeedbackProvider {

  private final String askForTextPage;
  private final String messageWasSentPage;
  private final String canceledPage;

  public FeedbackService(String deployUrl) {
    this.askForTextPage = String.format(Templates.ASK_FOR_TEXT_PAGE, deployUrl, deployUrl+"?canceled=true");
    this.messageWasSentPage = String.format(Templates.MESSAGE_PAGE, "Message was sent!");
    this.canceledPage = String.format(Templates.MESSAGE_PAGE, "Canceled!");
  }

  @Override
  public void sendAskForTextResponse(HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);

    try (PrintWriter out = response.getWriter()) {
      out.write(askForTextPage);
    }
  }

  @Override
  public void sendMessageWasSent(HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);

    try (PrintWriter out = response.getWriter()) {
      out.write(messageWasSentPage);
    }
  }

  @Override
  public void sendCanceled(HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);

    try (PrintWriter out = response.getWriter()) {
      out.write(canceledPage);
    }
  }
}
