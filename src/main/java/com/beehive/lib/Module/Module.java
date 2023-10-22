package com.beehive.lib.Module;

import com.beehive.lib.Controller.Controller;
import com.beehive.lib.Service.Service;

import java.util.ArrayList;
import java.util.List;

public final class Module {
  private final Module parent;
  private final Module[] modules;
  private final List<Class<?>> services = new ArrayList<>();
  private final List<Class<?>> controllers = new ArrayList<>();

  public Module(
    Module parent,
    Module[] modules,
    Class<?>[] services,
    Class<?>[] controllers
  ) {
    this.parent = parent;

    this.modules = modules;
    this.services.addAll(List.of(services));
    this.controllers.addAll(List.of(controllers));
  }

  public static Module create(Class<?> clazz, Module parent) {
    if (!clazz.isAnnotationPresent(com.beehive.annotations.Module.class)) {
      throw new ModuleNotFoundError(clazz.getName());
    }

    Class<?>[] moduleClasses = clazz.getAnnotation(com.beehive.annotations.Module.class).modules();

    Class<?>[] services = getServicesFromModule(clazz);
    Class<?>[] controllers = getControllersFromModule(clazz);

    Module[] modules = new Module[moduleClasses.length];

    Module module = new Module(parent, modules, services, controllers);

    for (int i = 0; i < moduleClasses.length; i++) {
      modules[i] = create(moduleClasses[i], module);
    }

    return module;
  }

  private static Class<?>[] getServicesFromModule(Class<?> clazz) {
    Class<?>[] services = clazz.getAnnotation(com.beehive.annotations.Module.class).services();

    for (Class<?> service : services) {
      Service.check(service);
    }

    return services;
  }

  private static Class<?>[] getControllersFromModule(Class<?> clazz) {
    Class<?>[] controllers = clazz.getAnnotation(com.beehive.annotations.Module.class).controllers();

    for (Class<?> controller : controllers) {
      Controller.check(controller);
    }

    return controllers;
  }

  public List<Class<?>> getAllServiceClasses() {
    List<Class<?>> allServices = new ArrayList<>();

    for (Module module : modules) {
      allServices.addAll(module.getAllServiceClasses());
    }

    allServices.addAll(services);

    return allServices;
  }
}
