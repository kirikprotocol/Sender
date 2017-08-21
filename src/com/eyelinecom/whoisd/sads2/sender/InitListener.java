package com.eyelinecom.whoisd.sads2.sender;

import com.eyeline.utils.config.ConfigException;
import com.eyeline.utils.config.xml.XmlConfig;
import com.eyeline.utils.config.xml.XmlConfigSection;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * author: Artem Voronov
 */
public class InitListener implements ServletContextListener {

  private static final String PROPERTY_CONFIG_DIR     = "sender.plugin.config.dir";
  private static final String DEFAULT_CONFIG_DIR      = "conf";
  private static final String PROPERTIES_FILE_NAME    = "config.xml";

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    final File configDir = getConfigDir();
    initLog4j(configDir);
    XmlConfigSection config = loadXmlConfig(configDir);
    initWebContext(config);
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
  }

  private File getConfigDir() {
    String configDir = System.getProperty(PROPERTY_CONFIG_DIR);
    if (configDir == null) {
      configDir = DEFAULT_CONFIG_DIR;
      System.err.println("System property '" + PROPERTY_CONFIG_DIR + "' is not set. Using default value: " + configDir);
    }
    File cfgDir = new File(configDir);

    if (!cfgDir.exists())
      throw new RuntimeException("Config directory '" + cfgDir.getAbsolutePath() + "' does not exist");

    System.out.println("Using properties directory '" + cfgDir.getAbsolutePath() + "'");
    return cfgDir;
  }

  private XmlConfigSection loadXmlConfig(File configDir) {
    final File cfgFile = new File(configDir, PROPERTIES_FILE_NAME);
    XmlConfigSection result;
    try {
      XmlConfig cfg = new XmlConfig();
      cfg.load(cfgFile);
      result = cfg.getSection("sender.plugin");
    } catch (ConfigException e) {
      throw new RuntimeException("Unable to load config.xml", e);
    }
    return result;
  }

  private void initLog4j(File configDir) {
    final File log4jProps = new File(configDir, "log4j.properties");
    System.out.println("Log4j conf file: " + log4jProps.getAbsolutePath() + ", exists: " + log4jProps.exists());
    PropertyConfigurator.configureAndWatch(log4jProps.getAbsolutePath(), TimeUnit.MINUTES.toMillis(1));
  }

  private void initWebContext(XmlConfigSection config) {
    try {

      WebContext.init(config);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Can't init WebContext", e);
    }
  }

}

