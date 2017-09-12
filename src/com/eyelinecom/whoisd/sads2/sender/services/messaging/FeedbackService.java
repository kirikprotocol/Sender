package com.eyelinecom.whoisd.sads2.sender.services.messaging;

import com.eyelinecom.whoisd.sads2.sender.services.i18n.MessageProvider;
import com.eyelinecom.whoisd.sads2.sender.utils.EncodingUtils;
import com.eyelinecom.whoisd.sads2.sender.utils.Templates;
import com.eyelinecom.whoisd.sads2.sender.web.servlets.RequestParameters;
import org.apache.log4j.Logger;

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

  private final static Logger logger = Logger.getLogger("SADS_SENDER");

  private final String deployUrl;
  private final MessageProvider messageProvider;

  public FeedbackService(String deployUrl, MessageProvider messageProvider) {
    this.deployUrl = deployUrl;
    this.messageProvider = messageProvider;
  }

  @Override
  public void sendAskForTextResponse(Locale locale, HttpServletResponse response, String exitUrl, String senderOwner) throws IOException {
    String lang = locale.getLanguage();
    final String xml = String.format(Templates.ASK_FOR_TEXT_PAGE,
      messageProvider.getString(lang, "enter.message"), deployUrl + "?exit_url=" + EncodingUtils.encode(exitUrl) + "&amp;sender_service_owner=" + EncodingUtils.encode(senderOwner),
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

  @Override
  public void sendAccessDenied(Locale locale, HttpServletResponse response) throws IOException {
    String lang = locale.getLanguage();
    String xml = String.format(Templates.MESSAGE_PAGE, messageProvider.getString(lang, "access.denied") );

    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    try (PrintWriter out = response.getWriter()) {
      out.write(xml);
    }
  }

  @Override
  public void sendOnlyTextMessagesAreSupported(Locale locale, HttpServletResponse response) throws IOException {
    String lang = locale.getLanguage();
    String xml = String.format(Templates.MESSAGE_PAGE, messageProvider.getString(lang, "only.text.messages.are.supported") );

    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    try (PrintWriter out = response.getWriter()) {
      out.write(xml);
    }
  }

  @Override
  public void sendToMsisdnVerification(HttpServletResponse response, RequestParameters params) throws IOException {
    final String successUrl = deployUrl + "?"+paramsToString(params.getPluginParams());
    final String redirectUri = "http://plugins.miniapps.run/msisdn-verification?type=c2s&success_url="+successUrl;

    if (logger.isDebugEnabled())
      logger.debug("Redirecting to: " + redirectUri);

    response.sendRedirect(redirectUri);
  }

    private static String paramsToString(Map<String, String> parameters) {
    return parameters.entrySet().stream().map(stringEntry -> "" + stringEntry.getKey() + "%3D"+stringEntry.getValue()).collect(Collectors.joining("%26"));
  }
}
