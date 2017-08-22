package com.eyelinecom.whoisd.sads2.sender.services.messaging;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * author: Artem Voronov
 */
public class FeedbackService implements FeedbackProvider {

  private static final String ASK_FOR_TEXT_PAGE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
    "<page version=\"2.0\">" +
      "<div>" +
        "<input navigationId=\"submit\" name=\"sender_message\" title=\"Enter message:\" />" +
      "</div>" +
      "<navigation id=\"submit\">" +
        "<link accesskey=\"1\" pageId=\"%s\">Ok</link>" +
      "</navigation>" +
      "<navigation>" +
        "<link accesskey=\"2\" pageId=\"%s\">Cancel</link>" +
      "</navigation>" +
    "</page>";

  private static final String MESSAGE_PAGE = "<?xml version=\"1.0\" encoding=\"utf-8\"?><page version=\"2.0\"><div>%s</div></page>";

  private final String deployUrl;

  public FeedbackService(String deployUrl) {
    this.deployUrl = deployUrl;
  }

  @Override
  public void sendAskForTextResponse(HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);

    String xml = String.format(ASK_FOR_TEXT_PAGE, deployUrl, deployUrl+"?canceled=true");
    try (PrintWriter out = response.getWriter()) {
      out.write(xml);
    }
  }

  @Override
  public void sendMessageWasSent(HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);

    try (PrintWriter out = response.getWriter()) {
      out.write(String.format(MESSAGE_PAGE, "Message was sent!"));
    }
  }

  @Override
  public void sendCanceled(HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);

    try (PrintWriter out = response.getWriter()) {
      out.write(String.format(MESSAGE_PAGE, "Canceled!"));
    }
  }
}
