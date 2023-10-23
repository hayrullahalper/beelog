package com.beehive.errors.controller;

public class ControllerInheritanceError extends Error {
  public ControllerInheritanceError(String className) {
    super("Controller " + className + " does not inherit from Controller");
  }
}
