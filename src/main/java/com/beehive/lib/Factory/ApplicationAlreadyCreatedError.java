package com.beehive.lib.Factory;

public class ApplicationAlreadyCreatedError extends Error {
  public ApplicationAlreadyCreatedError() {
    super("Application instance already created!");
  }
}
