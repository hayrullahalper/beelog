package com.beehive.errors.controller;

public class ControllerPathAnnotationNotFoundError extends Error {
  public ControllerPathAnnotationNotFoundError(String className) {
    super("Controller " + className + " is not annotated with @Path");
  }
}
