package com.beehive.lib.Application;

public class ServiceAccessError extends Error {
  public ServiceAccessError(String className, String injector) {
    super("Service " + className + " is not accessible from " + injector);
  }
}
