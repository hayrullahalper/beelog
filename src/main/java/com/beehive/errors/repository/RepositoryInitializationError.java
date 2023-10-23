package com.beehive.errors.repository;

public class RepositoryInitializationError extends Error {
  public RepositoryInitializationError(String className) {
    super(className + " could not be initialized");
  }
}
