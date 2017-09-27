package com.eyelinecom.whoisd.sads2.sender.web.servlets;

import com.eyelinecom.whoisd.sads2.sender.services.messaging.FeedbackProvider;
import com.eyelinecom.whoisd.sads2.sender.services.profile.ProfileApiProvider;
import com.eyelinecom.whoisd.sads2.sender.services.profile.ProfileProperty;
import com.eyelinecom.whoisd.sads2.sender.services.sender.SenderProvider;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * author: Artem Voronov
 */
public class SenderServlet extends HttpServlet {

  private final static Logger logger = Logger.getLogger("SADS_SENDER");

  @Inject
  @Named("feedback")
  private FeedbackProvider feedbackProvider;

  @Inject
  @Named("sender")
  private SenderProvider senderProvider;

  @Inject
  @Named("profile")
  private ProfileApiProvider profileApiProvider;

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

      if (logger.isDebugEnabled())
        logger.debug("Request: " + params);

      final String senderServiceOwner = params.getSenderServiceOwner();
      final Locale locale = params.getLocale();
      if (senderServiceOwner == null || senderServiceOwner.isEmpty()) {
        feedbackProvider.sendPleaseAddServiceOwner(locale, response);
        return;
      }

      ProfileProperty msisdnProperty = profileApiProvider.getProfileProperty(params.getUserId(),"mobile.msisdn");
      if (msisdnProperty == null) {
        feedbackProvider.sendToMsisdnVerification(response, params);
        return;
      }

      String msisdn = msisdnProperty.getValue();
      if (msisdn == null || msisdn.isEmpty()) {
        feedbackProvider.sendToMsisdnVerification(response, params);
        return;
      }

      if (isAccessDenied(params, msisdn)) {
        feedbackProvider.sendAccessDenied(locale, response);
        return;
      }

      if (params.hasSenderMessage()) {

        if (params.getMessageType() == null) {//only text messages are supported
          feedbackProvider.sendOnlyTextMessagesAreSupported(locale, response);
          return;
        }

        Map<String, Integer> frequency = senderProvider.initMessageBroadcasting(params.getServiceId(), params.getSenderMessage());
        feedbackProvider.sendNotifyAllResult(locale, response, frequency);
      }
      else {
        feedbackProvider.sendAskForTextResponse(locale, response, params.getExitUrl(), senderServiceOwner);
      }

    } catch(Exception ex) {
      logger.error(ex.getMessage(), ex);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }


  private boolean isAccessDenied(RequestParameters params, String msisdn) {
    final String serviceOwner = params.getSenderServiceOwner();

    if (serviceOwner == null) {
      if (logger.isDebugEnabled())
        logger.debug("Missed \"sender-service-owner\" property!");

      return true;
    }

    final String verifiedPhoneNumber = msisdn.replaceAll("\\+", "").replaceAll("-", "").replaceAll("\\s", "");
    final String serviceOwnerFormatted = params.getSenderServiceOwner().replaceAll("\\+", "").replaceAll("-", "").replaceAll("\\s", "");

    if (!verifiedPhoneNumber.equals(serviceOwnerFormatted)) {
      if (logger.isDebugEnabled())
        logger.debug("VERIFIED_PHONE_NUMBER is not equal to SERVICE_OWNER!");

      return true;
    }

    return false;
  }
}
