package com.beehive.lib.application;

import com.beehive.errors.application.BeehiveApplicationAlreadyInitializedError;
import com.beehive.errors.application.BeehiveApplicationNotFoundError;

public class BeehiveApplicationFactory {
  private static BeehiveApplication instance = null;

  public static synchronized BeehiveApplication getInstance() {
    if (instance == null) {
      throw new BeehiveApplicationNotFoundError();
    }

    return instance;
  }

  public static BeehiveApplication create(Class<?> module, BeehiveConfig config) {
    if (instance != null) {
      throw new BeehiveApplicationAlreadyInitializedError();
    }

    instance = new BeehiveApplication(module, config);

    return instance;
  }
}
