package com.beehive.errors.service;

public class ServiceInheritanceError extends Error {
  public ServiceInheritanceError(String className) {
    super("Service " + className + " must inherit from Service class");
  }
}
