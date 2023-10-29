package com.beehive.lib.controller;

import com.beehive.errors.controller.ControllerInheritanceError;
import com.beehive.errors.controller.ControllerPathAnnotationNotFoundError;
import com.beehive.lib.application.BeehiveApplicationFactory;
import com.beehive.lib.injector.Injector;
import com.beehive.lib.service.Service;

import javax.ws.rs.Path;
import java.lang.reflect.Field;
import java.util.stream.Stream;

public abstract class Controller extends Injector {
  public static void check(Class<?> clazz) {
    if (!clazz.isAnnotationPresent(Path.class)) {
      throw new ControllerPathAnnotationNotFoundError(clazz.getName());
    }

    if (!Controller.class.isAssignableFrom(clazz)) {
      throw new ControllerInheritanceError(clazz.getName());
    }
  }

  @SuppressWarnings("unchecked")
  public static void fieldCheck(Class<?> clazz) {
    var controller = (Class<? extends Controller>) clazz;
    var serviceLoader = BeehiveApplicationFactory.getInstance().getServiceLoader();

    Stream.of(clazz.getDeclaredFields())
      .map(Field::getType)
      .filter(Service.class::isAssignableFrom)
      .map(service -> (Class<? extends Service>) service)
      .forEach(service -> serviceLoader.checkController(controller, service));
  }
}
