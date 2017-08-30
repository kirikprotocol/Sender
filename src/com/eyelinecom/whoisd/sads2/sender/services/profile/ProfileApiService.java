package com.eyelinecom.whoisd.sads2.sender.services.profile;

import com.eyelinecom.whoisd.sads2.sender.services.api.ApiClient;

import java.util.Collection;
import java.util.Map;

/**
 * author: Artem Voronov
 */
public class ProfileApiService implements ProfileApiProvider {

  private final ApiClient apiClient;
  private final String profileStorageApiUrl;

  public ProfileApiService(ApiClient apiClient, String profileStorageApiUrl) {
    this.apiClient = apiClient;
    this.profileStorageApiUrl = profileStorageApiUrl;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Collection<String> getProfiles(String serviceId) {
    final String escapedServiceId = serviceId.replaceAll("\\.", "_");
    return apiClient.get(profileStorageApiUrl+"?visited."+escapedServiceId, Collection.class);
  }

  @Override
  @SuppressWarnings("unchecked")
  public ProfileProperty getProfileProperty(String profileId, String property) {

    Map<String, Object> profileProperty = apiClient.get(profileStorageApiUrl+"?"+property, Map.class);
    String value = profileProperty.get("value").toString();
    String path = profileProperty.get("path").toString();

    return new ProfileProperty(value, path);
  }

}
