package com.beehive.errors.service;

public class ServiceComplexityError extends Error {
  public ServiceComplexityError() {
    super("Service complexity is too high");
  }
}
