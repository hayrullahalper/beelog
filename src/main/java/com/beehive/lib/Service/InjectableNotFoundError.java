package com.beehive.lib.Service;

public class InjectableNotFoundError extends Error {
    public InjectableNotFoundError(String className) {
        super("Service " + className + " is not annotated with @Injectable");
    }
}
