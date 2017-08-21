package com.eyelinecom.whoisd.sads2.sender.web.servlets;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * author: Artem Voronov
 */
public class SenderServlet extends HttpServlet {

  protected final static Logger logger = Logger.getLogger("SADS_SENDER");

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    handleRequest(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    handleRequest(req, resp);
  }

  private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //TODO
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("text; charset=utf-8");
    try (PrintWriter out = response.getWriter()) {
      out.write("sads sender has started");
    }
  }
}
