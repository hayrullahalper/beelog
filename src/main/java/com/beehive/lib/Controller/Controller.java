package com.beehive.lib.Controller;

import com.beehive.lib.Injector.Injector;

import javax.ws.rs.Path;

public abstract class Controller extends Injector {
    public static void check(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Path.class)) {
            throw new PathNotFoundError(clazz.getName());
        }

        if (!Controller.class.isAssignableFrom(clazz)) {
            throw new ControllerInheritError(clazz.getName());
        }
    }
}
