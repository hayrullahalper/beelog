package com.beehive.errors.application;

import com.beehive.lib.service.Service;

public class BeehiveServiceLoaderInsufficientAccessError extends Error {
  public BeehiveServiceLoaderInsufficientAccessError(Class<?> injector, Class<? extends Service> service) {
    super(
      "Insufficient access to inject " + service.getName() + " into " + injector.getName() + "."
    );
  }
}
