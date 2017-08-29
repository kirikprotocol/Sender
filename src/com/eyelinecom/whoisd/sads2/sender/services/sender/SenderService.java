package com.eyelinecom.whoisd.sads2.sender.services.sender;

import com.eyelinecom.whoisd.sads2.sender.utils.EncodingUtils;
import com.eyelinecom.whoisd.sads2.sender.utils.Templates;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * author: Artem Voronov
 */
public class SenderService implements SenderProvider {

  private final static Logger logger = Logger.getLogger("SADS_SENDER");

  private final Client client;
  private final String profileStorageApiUrl;
  private final String pushApiUrl;

  private final Set<String> forbiddenProtocols = new HashSet<>(Arrays.asList(new String[]{"xhtml_mp"}));

  public SenderService(String profileStorageApiUrl, String pushApiUrl, int connectTimeoutInMillis, int requestTimeoutInMillis) {
    ClientConfig config = new ClientConfig();
    config.property(ClientProperties.CONNECT_TIMEOUT, connectTimeoutInMillis);
    config.property(ClientProperties.READ_TIMEOUT, requestTimeoutInMillis);
    config.property(ClientProperties.USE_ENCODING, "UTF-8");
    this.client = ClientBuilder.newBuilder().withConfig(config).build();
    this.profileStorageApiUrl = profileStorageApiUrl;
    this.pushApiUrl = pushApiUrl;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Integer> initMessageBroadcasting(String serviceId, String message) {
    final String escapedServiceId = serviceId.replaceAll("\\.", "_");
    Collection<String> userIds = get(profileStorageApiUrl+"?visited."+escapedServiceId, Collection.class);

    Map<String, Integer> frequency = new HashMap<>();

    final String encodedMessagePage = EncodingUtils.encode(String.format(Templates.MESSAGE_PAGE, message));
    for (String userId : userIds) {
      Map<String, Object> profileProperty = get(profileStorageApiUrl+"/"+userId+"/visited."+escapedServiceId, Map.class);
      String path = profileProperty.get("path").toString();
      if (path != null && path.startsWith("visited")) {
        String protocol = profileProperty.get("value").toString();
        if (protocol != null && !protocol.isEmpty() && !forbiddenProtocols.contains(protocol)) {

          int count = frequency.containsKey(protocol) ? frequency.get(protocol) : 0;
          frequency.put(protocol, count + 1);

          String pushUrl = pushApiUrl+"?service=" + serviceId + "&user_id=" + userId + "&protocol=" + protocol + "&scenario=xmlpush&document=" + encodedMessagePage;
          get(pushUrl, String.class);
        }
      }
    }

    return frequency;
  }

  private <T> T get(String endpoint, Class<T> clazz) {
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
