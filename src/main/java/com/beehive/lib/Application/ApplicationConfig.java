package com.beehive.lib.Application;

import java.net.URI;

public final class ApplicationConfig {

  private String HOST = "localhost";
  private String PROTOCOL = "http";
  private MODE mode = MODE.DEVELOPMENT;
  private String mainPackage;
  private String persistenceUnit;
  public ApplicationConfig(String mainPackage, String persistenceUnit) {
    this.mainPackage = "com.beelog";
  }

  public String getHOST() {
    return HOST;
  }

  public void setHOST(String HOST) {
    this.HOST = HOST;
  }

  public String getPROTOCOL() {
    return PROTOCOL;
  }

  public void setPROTOCOL(String PROTOCOL) {
    this.PROTOCOL = PROTOCOL;
  }

  public MODE getMode() {
    return mode;
  }

  public void setMode(MODE mode) {
    this.mode = mode;
  }

  public String getPersistenceUnit() {
    return persistenceUnit;
  }

  public String getMainPackage() {
    return mainPackage;
  }

  public URI getBaseURI(int port) {
    return URI.create(String.format("%s://%s:%d/", this.PROTOCOL, this.HOST, port));
  }

  public enum MODE {
    DEVELOPMENT,
    PRODUCTION,
    DEBUG
  }
}
