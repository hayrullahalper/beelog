package com.beehive.errors.application;

public class BeehiveApplicationAlreadyInitializedError extends Error {
  public BeehiveApplicationAlreadyInitializedError() {
    super("Beehive application already initialized");
  }
}
