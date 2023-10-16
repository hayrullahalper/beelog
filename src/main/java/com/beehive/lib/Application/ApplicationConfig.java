package com.beehive.lib.Application;

import java.net.URI;

public final class ApplicationConfig {

    public enum MODE {
        DEVELOPMENT,
        PRODUCTION,
        DEBUG
    }

    private String HOST = "localhost";
    private String PROTOCOL = "http";
    private MODE mode = MODE.DEVELOPMENT;
    private String mainPackage;
    private String persistenceUnit;

    public ApplicationConfig(String mainPackage, String persistenceUnit) {
        this.mainPackage = "com.playground";
    }

    public String getHOST() {
        return HOST;
    }

    public String getPROTOCOL() {
        return PROTOCOL;
    }

    public MODE getMode() {
        return mode;
    }

    public String getPersistenceUnit() {
        return persistenceUnit;
    }

    public String getMainPackage() {
        return mainPackage;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public void setPROTOCOL(String PROTOCOL) {
        this.PROTOCOL = PROTOCOL;
    }

    public void setMode(MODE mode) {
        this.mode = mode;
    }

    public URI getBaseURI(int port) {
        return URI.create(String.format("%s://%s:%d/", this.PROTOCOL, this.HOST, port));
    }
}
