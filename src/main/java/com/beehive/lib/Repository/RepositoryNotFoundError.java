package com.beehive.lib.Repository;

public class RepositoryNotFoundError extends Error {
  public RepositoryNotFoundError(String className) {
    super("Repository not found: " + className);
  }
}
