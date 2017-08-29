package com.eyelinecom.whoisd.sads2.sender.services.i18n;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * author: Artem Voronov
 */
public class MessageProvider {

  private static final Logger logger = Logger.getLogger("SADS_SENDER");
  private static final String RESOURCE_BUNDLE_TEMPLATE = "messages_%s.properties";
  private static final Map<String, PropertyResourceBundle> bundles = new HashMap<>();
  private static final List<String> supportedLangs = Arrays.asList("en", "ru");

  public MessageProvider() {
    for (String lang : supportedLangs) {
      String path = String.format(RESOURCE_BUNDLE_TEMPLATE, lang);
      try (InputStream is = MessageProvider.class.getResourceAsStream(path)) {
        try (InputStreamReader ir = new InputStreamReader(is, "UTF8")) {
          PropertyResourceBundle resourceBundle = new PropertyResourceBundle(ir);
          bundles.put(lang, resourceBundle);
        }

      } catch (IOException e) {
        logger.error("Unable to load resource bundles", e);
      }
    }
  }

  public String getString(String lang, String key) {
    PropertyResourceBundle bundle = getResourceBundle(lang);
    return bundle.getString(key);
  }

  private PropertyResourceBundle getResourceBundle(String lang) {
    if (lang == null || !supportedLangs.contains(lang))
      lang = "en";

    return bundles.get(lang);
  }
}
