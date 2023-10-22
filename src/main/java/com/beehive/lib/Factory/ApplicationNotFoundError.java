package com.beehive.lib.Factory;

public class ApplicationNotFoundError extends Error {
  public ApplicationNotFoundError() {
    super("Application instance not found. Did you forget to call Factory.create()?");
  }
}
