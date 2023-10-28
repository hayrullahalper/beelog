package com.beehive.errors.application;

public class BeehiveServiceLoaderInsufficientAccessError extends Error {
  public BeehiveServiceLoaderInsufficientAccessError(String injector, String service) {
    super(
      "Insufficient access to inject " + service + " into " + injector + "."
    );
  }
}
