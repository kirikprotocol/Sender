package com.eyelinecom.whoisd.sads2.sender.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * author: Artem Voronov
 */
public class EncodingUtils {
  public static String encode(String value) {
    try {
      return URLEncoder.encode(value.replaceAll("%", "&#37;").replaceAll("\\+", "&#43;"), StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      throw new IllegalArgumentException("Value can not be encoded: " + value);
    }
  }
}
