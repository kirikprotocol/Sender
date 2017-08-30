package com.eyelinecom.whoisd.sads2.sender.services.api;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

/**
 * author: Artem Voronov
 */
public class ApiClient {

  private final static Logger logger = Logger.getLogger("SADS_SENDER");

  private final Client client;

  public ApiClient(int connectTimeoutInMillis, int requestTimeoutInMillis) {
    ClientConfig config = new ClientConfig();
    config.property(ClientProperties.CONNECT_TIMEOUT, connectTimeoutInMillis);
    config.property(ClientProperties.READ_TIMEOUT, requestTimeoutInMillis);
    config.property(ClientProperties.USE_ENCODING, "UTF-8");
    this.client = ClientBuilder.newBuilder().withConfig(config).build();
  }

  public <T> T get(String endpoint, Class<T> clazz) {
    if (logger.isDebugEnabled())
      logger.debug("Request to: " + endpoint);

    T result = null;
    Response response = null;
    try {
      Invocation invocation = client.target(endpoint)
        .request()
        .buildGet();

      response = invocation.invoke();
      result = response.hasEntity() ? response.readEntity(clazz) : null;

      if (result == null)
        throw new IllegalArgumentException("Null response");

      if (logger.isDebugEnabled())
        logger.debug("Response: " + result);

    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    } finally {
      if (response != null)
        response.close();
    }

    return result;
  }
}
