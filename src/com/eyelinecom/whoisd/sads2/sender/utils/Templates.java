package com.eyelinecom.whoisd.sads2.sender.utils;

/**
 * author: Artem Voronov
 */
public class Templates {
  public static final String ASK_FOR_TEXT_PAGE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
    "<page version=\"2.0\">" +
    "<div>" +
    "<input navigationId=\"submit\" name=\"sender_message\" title=\"Enter message:\" />" +
    "</div>" +
    "<navigation id=\"submit\">" +
    "<link accesskey=\"1\" pageId=\"%s\">Ok</link>" +
    "</navigation>" +
    "<navigation>" +
    "<link accesskey=\"2\" pageId=\"%s\">Cancel</link>" +
    "</navigation>" +
    "</page>";

  public static final String MESSAGE_PAGE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
    "<page version=\"2.0\">" +
      "<div>" +
        "%s" +
      "</div>" +
    "</page>";
}
