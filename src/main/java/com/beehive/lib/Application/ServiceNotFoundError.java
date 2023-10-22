package com.beehive.lib.Application;

public class ServiceNotFoundError extends Error {
  public ServiceNotFoundError(String className) {
    super("Requested service " + className + " is not found in the application");
  }
}
