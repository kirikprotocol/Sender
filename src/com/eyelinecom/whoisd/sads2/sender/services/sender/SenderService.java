package com.eyelinecom.whoisd.sads2.sender.services.sender;

import com.eyelinecom.whoisd.sads2.sender.services.api.ApiClient;
import com.eyelinecom.whoisd.sads2.sender.services.profile.ProfileApiProvider;
import com.eyelinecom.whoisd.sads2.sender.services.profile.ProfileProperty;
import com.eyelinecom.whoisd.sads2.sender.utils.EncodingUtils;
import com.eyelinecom.whoisd.sads2.sender.utils.Templates;
import java.util.*;

/**
 * author: Artem Voronov
 */
public class SenderService implements SenderProvider {

  private final String pushApiUrl;
  private final ProfileApiProvider profileApiProvider;
  private final ApiClient apiClient;

  private final Set<String> forbiddenProtocols = new HashSet<>(Arrays.asList(new String[]{"xhtml_mp"}));

  public SenderService(ProfileApiProvider profileApiProvider, ApiClient apiClient, String pushApiUrl) {
    this.profileApiProvider = profileApiProvider;
    this.apiClient = apiClient;
    this.pushApiUrl = pushApiUrl;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Integer> initMessageBroadcasting(String serviceId, String message) {
    final String escapedServiceId = serviceId.replaceAll("\\.", "_");
    Collection<String> profileIds = profileApiProvider.getProfiles(serviceId);

    Map<String, Integer> frequency = new HashMap<>();

    final String encodedMessagePage = EncodingUtils.encode(String.format(Templates.MESSAGE_PAGE, message));
    for (String profileId : profileIds) {
      ProfileProperty profileProperty = profileApiProvider.getProfileProperty(profileId,"/visited."+escapedServiceId);
      String path = profileProperty.getPath();
      if (path != null && path.startsWith("visited")) {
        String protocol = profileProperty.getValue();
        if (protocol != null && !protocol.isEmpty() && !forbiddenProtocols.contains(protocol)) {

          int count = frequency.containsKey(protocol) ? frequency.get(protocol) : 0;
          frequency.put(protocol, count + 1);

          String pushUrl = pushApiUrl+"?service=" + serviceId + "&user_id=" + profileId + "&protocol=" + protocol + "&scenario=xmlpush&document=" + encodedMessagePage;
          apiClient.get(pushUrl, String.class);
        }
      }
    }

    return frequency;
  }

}
