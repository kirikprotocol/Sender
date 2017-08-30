package com.eyelinecom.whoisd.sads2.sender.services.profile;

/**
 * author: Artem Voronov
 */
public class ProfileProperty {
  private final String value;
  private final String path;

  public ProfileProperty(String value, String path) {
    this.value = value;
    this.path = path;
  }

  public String getValue() {
    return value;
  }

  public String getPath() {
    return path;
  }
}
