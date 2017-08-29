package com.eyelinecom.whoisd.sads2.sender.services.messaging;

import com.eyelinecom.whoisd.sads2.sender.utils.EncodingUtils;
import com.eyelinecom.whoisd.sads2.sender.utils.Templates;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Collectors;

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

    final String askForTextPage = String.format(Templates.ASK_FOR_TEXT_PAGE, deployUrl + "?exit_url=" + EncodingUtils.encode(exitUrl), exitUrl);
    try (PrintWriter out = response.getWriter()) {
      out.write(askForTextPage);
    }
  }

  @Override
  public void sendNotifyAllResult(HttpServletResponse response, Map<String, Integer> frequency) throws IOException {
    int totalUsers = frequency.entrySet().stream().mapToInt(Map.Entry::getValue).sum();
    String frequencyFormatted = frequency.entrySet().stream().map(e-> e.getKey() + " - " + e.getValue()).collect(Collectors.joining("<br/>"));
    String msg = "Total users: " + totalUsers + "<br/>Users by channels:<br/>" + frequencyFormatted;

    String notifyAllResultMessage = String.format(Templates.MESSAGE_PAGE, msg);
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);

    try (PrintWriter out = response.getWriter()) {
      out.write(notifyAllResultMessage);
    }
  }
}
