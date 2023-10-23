package com.beehive.errors.application;

public class BeehiveApplicationNotFoundError extends Error {
  public BeehiveApplicationNotFoundError() {
    super("BeehiveApplication not found. Did you forget to call BeehiveApplicationFactory.create()?");
  }
}
