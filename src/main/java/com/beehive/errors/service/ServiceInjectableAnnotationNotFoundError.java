package com.beehive.errors.service;

public class ServiceInjectableAnnotationNotFoundError extends Error {
  public ServiceInjectableAnnotationNotFoundError(String className) {
    super("The class " + className + " must be annotated with @Injectable");
  }
}
