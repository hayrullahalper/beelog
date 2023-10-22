package com.beehive.lib.Controller;

public class PathNotFoundError extends Error {
  public PathNotFoundError(String className) {
    super("Controller " + className + " is not annotated with @Path");
  }
}
