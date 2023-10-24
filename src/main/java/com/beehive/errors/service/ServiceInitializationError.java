package com.beehive.errors.service;

public class ServiceInitializationError extends Error {
  public ServiceInitializationError(String className) {
    super(className + " could not be initialized");
  }
}
