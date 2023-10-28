package com.beehive.lib.application;

import com.beehive.annotations.Injectable;
import com.beehive.errors.application.BeehiveServiceLoaderInsufficientAccessError;
import com.beehive.lib.controller.Controller;
import com.beehive.lib.module.Module;
import com.beehive.lib.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public final class BeehiveServiceLoader {
  private static final Logger logger = LogManager.getLogger();

  private final Module module;
  private final List<Service> services = new ArrayList<>();

  BeehiveServiceLoader(Module module) {
    this.module = module;
  }

  public void load() {
    Service.sort(Module.getServicesRecursive(module)).forEach(service -> {
      Service.check(service);

      Service s = Service.create(service);

      this.services.add(s);
      logger.info("Service [{}] loaded", service.getName());
    });

    Module.lazyCheck(module);
  }

  public <T extends Service> T get(Class<T> clazz) {
    return this.services.stream()
      .filter(s -> s.getClass().equals(clazz))
      .map(clazz::cast)
      .findFirst()
      .orElse(null);
  }

  public void checkService(Class<? extends Service> injector, Class<? extends Service> service) {
  }

  public void checkController(Class<? extends Controller> injector, Class<? extends Service> service) {
    Module module = Module.findByController(injector, this.module);

    while (module != null) {
      if (module.getServices().contains(service)) {
        return;
      }

      var permission = service.getAnnotation(Injectable.class).value();

      if (permission.equals(Injectable.Permission.PRIVATE)) {
        throw new BeehiveServiceLoaderInsufficientAccessError(injector.getName(), service.getName());
      }

      module = module.getParent();
    }

    throw new BeehiveServiceLoaderInsufficientAccessError(injector.getName(), service.getName());
  }
}
