package com.eyelinecom.whoisd.sads2.sender.services.messaging;

import com.eyelinecom.whoisd.sads2.sender.utils.Templates;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * author: Artem Voronov
 */
public class FeedbackService implements FeedbackProvider {

  private final String deployUrl;

  public FeedbackService(String deployUrl) {
    this.deployUrl = deployUrl;
  }

  @Override
  public void sendAskForTextResponse(HttpServletResponse response, String exitUrl) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);

    final String askForTextPage = String.format(Templates.ASK_FOR_TEXT_PAGE, deployUrl, exitUrl);
    try (PrintWriter out = response.getWriter()) {
      out.write(askForTextPage);
    }
  }
}
