package com.eyelinecom.whoisd.sads2.sender.services;

import com.eyeline.utils.config.xml.XmlConfigSection;
import com.eyelinecom.whoisd.sads2.sender.services.api.ApiClient;
import com.eyelinecom.whoisd.sads2.sender.services.i18n.MessageProvider;
import com.eyelinecom.whoisd.sads2.sender.services.messaging.FeedbackProvider;
import com.eyelinecom.whoisd.sads2.sender.services.messaging.FeedbackService;
import com.eyelinecom.whoisd.sads2.sender.services.profile.ProfileApiProvider;
import com.eyelinecom.whoisd.sads2.sender.services.profile.ProfileApiService;
import com.eyelinecom.whoisd.sads2.sender.services.sender.SenderProvider;
import com.eyelinecom.whoisd.sads2.sender.services.sender.SenderService;

public class Services {

  private final SenderProvider senderProvider;
  private final FeedbackProvider feedbackProvider;
  private final ProfileApiProvider profileApiProvider;

  public Services(XmlConfigSection config) throws ServicesException {
    final ApiClient apiClient = initApiClient(config);
    final MessageProvider messageProvider = new MessageProvider();
    this.profileApiProvider = initProfileApiProvider(config, apiClient);
    this.senderProvider = initSenderProvider(config, profileApiProvider, apiClient);
    this.feedbackProvider = initFeedbackProvider(config, messageProvider);
  }

  private static ApiClient initApiClient(XmlConfigSection config) throws ServicesException {
    try {
      XmlConfigSection apiClientSection = config.getSection("api.client");
      int connectTimeout = apiClientSection.getInt("api.client.connectTimeout.millis");
      int requestTimeout = apiClientSection.getInt("api.client.requestTimeout.millis");
      return new ApiClient(connectTimeout, requestTimeout);
    }
    catch (Exception e) {
      throw new ServicesException("Error during ApiClient initialization.", e);
    }
  }

  private static ProfileApiProvider initProfileApiProvider(XmlConfigSection config, ApiClient apiClient) throws ServicesException {
    try {
      String profileStorageApiUrl = config.getString("profile.storage.api.url");
      return new ProfileApiService(apiClient, profileStorageApiUrl);
    }
    catch (Exception e) {
      throw new ServicesException("Error during ProfileApiProvider initialization.", e);
    }
  }

  private static SenderProvider initSenderProvider(XmlConfigSection config, ProfileApiProvider profileApiProvider, ApiClient apiClient) throws ServicesException {
    try {
      String pushApiUrl = config.getString("push.api.url");
      return new SenderService(profileApiProvider, apiClient, pushApiUrl);
    }
    catch (Exception e) {
      throw new ServicesException("Error during SenderProvider initialization.", e);
    }
  }

  private static FeedbackProvider initFeedbackProvider(XmlConfigSection config, MessageProvider messageProvider) throws ServicesException {
    try {
      String deployUrl = config.getString("deploy.url");
      return new FeedbackService(deployUrl, messageProvider);
    }
    catch (Exception e) {
      throw new ServicesException("Error during FeedbackProvider initialization.", e);
    }
  }

  public SenderProvider getSenderProvider() {
    return senderProvider;
  }

  public FeedbackProvider getFeedbackProvider() {
    return feedbackProvider;
  }

  public ProfileApiProvider getProfileApiProvider() {
    return profileApiProvider;
  }

}
