package com.eyelinecom.whoisd.sads2.sender.web.servlets;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * author: Artem Voronov
 */
public class RequestParameters {

  private final String serviceId;
  private final Locale locale;
  private final String userId;
  private final String senderMessage;
  private final String senderServiceOwner;
  private final String exitUrl;

  RequestParameters(HttpServletRequest request) {
    userId = getUserId(request);
    senderMessage = request.getParameter("sender_message");
    exitUrl = getRequiredParameter(request,"exit_url");
    senderServiceOwner = getRequiredParameter(request,"sender_service_owner");
    locale = getLocale(request);
    serviceId = getRequiredParameter(request, "service");
  }

  public String getServiceId() {
    return serviceId;
  }

  public Locale getLocale() {
    return locale;
  }

  public String getUserId() {
    return userId;
  }

  public String getSenderMessage() {
    return senderMessage;
  }

  public boolean hasSenderMessage() {
    return senderMessage != null && !senderMessage.trim().isEmpty();
  }

  public String getExitUrl() {
    return exitUrl;
  }

  public String getSenderServiceOwner() {
    return senderServiceOwner;
  }

  private static String getUserId(HttpServletRequest request) {
    String userId = request.getParameter("user_id");

    if(userId == null) {
      userId = request.getParameter("subscriber");
      if(userId == null)
        throw new IllegalArgumentException("No \"user_id\" parameter");
    }

    return userId;
  }

  private static Locale getLocale(HttpServletRequest request) {
    String locale = request.getParameter("locale");

    if(locale == null)
      locale = "en";

    return new Locale(locale);
  }

  private static String getRequiredParameter(HttpServletRequest request, String name) {
    String value = request.getParameter(name);

    if(value == null)
      throw new IllegalArgumentException("No \"" + name + "\" parameter");

    if(value.isEmpty())
      throw new IllegalArgumentException("Empty \"" + name + "\" parameter");

    return value;
  }

  @Override
  public String toString() {
    return "RequestParameters{" +
      "serviceId='" + serviceId + '\'' +
      ", locale=" + locale +
      ", userId='" + userId + '\'' +
      ", senderMessage='" + senderMessage + '\'' +
      ", exit_url='" + exitUrl + '\'' +
      '}';
  }
}
