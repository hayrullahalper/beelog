package com.beehive.errors.module;

public class ModuleAnnotationNotFoundError extends Error {
  public ModuleAnnotationNotFoundError(String className) {
    super("Module " + className + " is not annotated with @Module");
  }
}
