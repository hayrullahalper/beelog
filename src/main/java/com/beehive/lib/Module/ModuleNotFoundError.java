package com.beehive.lib.Module;

public class ModuleNotFoundError extends Error {
    public ModuleNotFoundError(String className) {
        super("Module " + className + " is not annotated with @Module");
    }
}
