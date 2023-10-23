package com.beehive.errors.application;

public class BeehiveApplicationConfigurationError extends Error {
  public BeehiveApplicationConfigurationError() {
    super("BeehiveConfig must have a main package and a persistence unit");
  }
}
