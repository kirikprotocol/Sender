package com.eyelinecom.whoisd.sads2.sender.services.profile;

import java.util.Collection;

/**
 * author: Artem Voronov
 */
public interface ProfileApiProvider {

  Collection<String> getProfiles(String serviceId);
  ProfileProperty getProfileProperty(String profileId, String proprty);
}
