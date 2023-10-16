package com.beehive.lib.Controller;

public class ControllerInheritError extends Error {
    public ControllerInheritError(String className) {
        super("Controller " + className + " is not inherited from Controller");
    }
}
