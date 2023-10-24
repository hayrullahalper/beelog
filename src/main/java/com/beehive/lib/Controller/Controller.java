package com.beehive.lib.Controller;

import com.beehive.errors.controller.ControllerInheritanceError;
import com.beehive.errors.controller.ControllerPathAnnotationNotFoundError;
import com.beehive.lib.Injector.Injector;

import javax.ws.rs.Path;

public abstract class Controller extends Injector {
  public static void check(Class<?> clazz) {
    if (!clazz.isAnnotationPresent(Path.class)) {
      throw new ControllerPathAnnotationNotFoundError(clazz.getName());
    }

    if (!Controller.class.isAssignableFrom(clazz)) {
      throw new ControllerInheritanceError(clazz.getName());
    }
  }
}
