package com.beehive.lib.Application;

public class ApplicatonServiceLoadError extends Error {
  public ApplicatonServiceLoadError(String serviceName) {
    super("Service " + serviceName + " has not been loaded into the application");
  }
}
