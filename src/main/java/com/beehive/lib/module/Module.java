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
  private final List<Class<?>> controllers = new ArrayList<>();
  private final Class<?> clazz;

  public Module(
    Class<?> clazz,

    Module parent,
    Module[] modules,
    Class<?>[] services,
    Class<?>[] controllers
  ) {
    this.clazz = clazz;
    this.parent = parent;
    this.modules = modules;

    List.of(services).forEach(Service::check);
    List.of(controllers).forEach(Controller::check);

    this.services.addAll(List.of(services));
    this.controllers.addAll(List.of(controllers));
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

    var module = new Module(clazz, parent, children, services, controllers);

    List.of(modules).forEach(child -> {
      int index = List.of(modules).indexOf(child);
      children[index] = create(child, module);
    });

    return module;
  }

  private static Class<?>[] getModulesFromModule(Class<?> clazz) {
    return clazz.getAnnotation(com.beehive.annotations.Module.class).modules();
  }

  private static Class<?>[] getServicesFromModule(Class<?> clazz) {
    return clazz.getAnnotation(com.beehive.annotations.Module.class).services();
  }

  private static Class<?>[] getControllersFromModule(Class<?> clazz) {
    return clazz.getAnnotation(com.beehive.annotations.Module.class).controllers();
  }

  public static List<Class<?>> getServicesRecursive(Module module) {
    List<Class<?>> services = new ArrayList<>();

    List.of(module.getModules()).forEach(child -> services.addAll(getServicesRecursive(child)));

    services.addAll(module.getServices());

    return services.stream().distinct().toList();
  }

  private static String extractPackageName(Class<?> clazz) {
    return clazz.getPackageName().substring(0, clazz.getPackageName().lastIndexOf("."));
  }

  private static String extractPackageName(Module module) {
    return module.getClazz().getPackageName().substring(0, module.getClazz().getPackageName().lastIndexOf("."));
  }

  public static Module findByService(Class<? extends Service> service, Module module) {
    if (module.getServices().contains(service) && extractPackageName(service).equals(extractPackageName(module))) {
      return module;
    }

    for (Module child : module.getModules()) {
      var result = findByService(service, child);

      if (result != null) {
        return result;
      }
    }

    return null;
  }

  public static Module findByController(Class<? extends Controller> controller, Module module) {
    if (module.getControllers().contains(controller)) {
      return module;
    }

    for (Module child : module.getModules()) {
      var result = findByController(controller, child);

      if (result != null) {
        return result;
      }
    }

    return null;
  }

  public static void lazyCheck(Module module) {
    module.getServices().forEach(Service::fieldCheck);
    module.getControllers().forEach(Controller::fieldCheck);

    for (Module child : module.getModules()) {
      lazyCheck(child);
    }
  }

  public Class<?> getClazz() {
    return clazz;
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

  public List<Class<?>> getControllers() {
    return controllers;
  }
}
