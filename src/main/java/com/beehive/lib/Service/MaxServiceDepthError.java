package com.beehive.lib.Service;

public class MaxServiceDepthError extends Error {
  public MaxServiceDepthError() {
    super("Maximum service depth exceeded. Check for circular dependencies.");
  }
}
