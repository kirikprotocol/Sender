package com.eyelinecom.whoisd.sads2.sender.services.messaging;

import com.eyelinecom.whoisd.sads2.sender.services.i18n.MessageProvider;
import com.eyelinecom.whoisd.sads2.sender.utils.EncodingUtils;
import com.eyelinecom.whoisd.sads2.sender.utils.Templates;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: Artem Voronov
 */
public class FeedbackService implements FeedbackProvider {

  private final String deployUrl;
  private final MessageProvider messageProvider;

  public FeedbackService(String deployUrl, MessageProvider messageProvider) {
    this.deployUrl = deployUrl;
    this.messageProvider = messageProvider;
  }

  @Override
  public void sendAskForTextResponse(Locale locale, HttpServletResponse response, String exitUrl) throws IOException {
    String lang = locale.getLanguage();
    final String xml = String.format(Templates.ASK_FOR_TEXT_PAGE,
      messageProvider.getString(lang, "enter.message"), deployUrl + "?exit_url=" + EncodingUtils.encode(exitUrl),
      exitUrl, messageProvider.getString(lang, "cancel"));

    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    try (PrintWriter out = response.getWriter()) {
      out.write(xml);
    }
  }

  @Override
  public void sendNotifyAllResult(Locale locale, HttpServletResponse response, Map<String, Integer> frequency) throws IOException {
    String lang = locale.getLanguage();
    int totalUsers = frequency.entrySet().stream().mapToInt(Map.Entry::getValue).sum();
    String frequencyFormatted = frequency.entrySet().stream().map(e-> e.getKey() + " - " + e.getValue()).collect(Collectors.joining("<br/>"));
    String msg = messageProvider.getString(lang, "total.users") + ": " + totalUsers + "<br/>" +
      messageProvider.getString(lang, "users.by.channels") + ":<br/>" + frequencyFormatted;
    String xml = String.format(Templates.MESSAGE_PAGE, msg);

    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    try (PrintWriter out = response.getWriter()) {
      out.write(xml);
    }
  }
}
