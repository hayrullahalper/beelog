package com.beehive.lib.application;

import java.net.URI;

public final class BeehiveConfig {
  private String protocol = "http";
  private String host = "localhost";
  private String main;
  private String unit;

  public static BeehiveConfig create(String main, String unit) {
    BeehiveConfig config = new BeehiveConfig();
    config.setMain(main);
    config.setUnit(unit);
    return config;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getMain() {
    return main;
  }

  public void setMain(String main) {
    this.main = main;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public URI getBaseURI(int port) {
    return URI.create(protocol + "://" + host + ":" + port + "/");
  }
}
