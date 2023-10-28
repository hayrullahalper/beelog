package com.beehive.lib.module;

import com.beehive.errors.module.ModuleAnnotationNotFoundError;
import com.beehive.lib.controller.Controller;
import com.beehive.lib.service.Service;

import java.util.ArrayList;
import java.util.List;

public final class Module {
  private final Module parent;
  private final Module[] modules;
  private final List<Class<?>> services = new ArrayList<>();

  public Module(
    Module parent,
    Module[] modules,
    Class<?>[] services,
    Class<?>[] controllers
  ) {
    this.parent = parent;
    this.modules = modules;

    List.of(services).forEach(Service::check);
    List.of(controllers).forEach(Controller::check);

    this.services.addAll(List.of(services));
  }

  public static void check(Class<?> clazz) {
    if (!clazz.isAnnotationPresent(com.beehive.annotations.Module.class)) {
      throw new ModuleAnnotationNotFoundError(clazz.getName());
    }
  }

  public static Module create(Class<?> clazz, Module parent) {
    Module.check(clazz);

    var modules = getModulesFromModule(clazz);
    var services = getServicesFromModule(clazz);
    var controllers = getControllersFromModule(clazz);

    var children = new Module[modules.length];

    var module = new Module(parent, children, services, controllers);

    List.of(modules).forEach(child -> {
      int index = List.of(modules).indexOf(child);
      children[index] = create(child, module);
    });

    return module;
  }

  private static Class<?>[] getModulesFromModule(Class<?> clazz) {
    Class<?>[] modules = clazz.getAnnotation(com.beehive.annotations.Module.class).modules();
    List.of(modules).forEach(Module::check);
    return modules;
  }

  private static Class<?>[] getServicesFromModule(Class<?> clazz) {
    Class<?>[] services = clazz.getAnnotation(com.beehive.annotations.Module.class).services();
    List.of(services).forEach(Service::check);
    return services;
  }

  private static Class<?>[] getControllersFromModule(Class<?> clazz) {
    Class<?>[] controllers = clazz.getAnnotation(com.beehive.annotations.Module.class).controllers();
    List.of(controllers).forEach(Controller::check);
    return controllers;
  }

  public static List<Class<?>> getAllServices(Module module) {
    List<Class<?>> services = new ArrayList<>();

    List.of(module.getModules()).forEach(child -> services.addAll(getAllServices(child)));

    services.addAll(module.getServices());

    return services.stream().distinct().toList();
  }

  public Module getParent() {
    return parent;
  }

  public Module[] getModules() {
    return modules;
  }

  public List<Class<?>> getServices() {
    return services;
  }
}
