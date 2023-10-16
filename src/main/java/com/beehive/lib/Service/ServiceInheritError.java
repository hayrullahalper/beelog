package com.beehive.lib.Service;

public class ServiceInheritError extends Error {
    public ServiceInheritError(String className) {
        super("Class " + className + " is not inherited from Service");
    }
}
