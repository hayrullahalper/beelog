package com.beehive.lib.Factory;

import com.beehive.lib.Application.Application;
import com.beehive.lib.Application.ApplicationConfig;

public class Factory {
    private static Application instance = null;

    public static synchronized Application getInstance() {
        if (instance == null) {
            throw new ApplicationNotFoundError();
        }

        return instance;
    }

    public static Application create(Class<?> module, ApplicationConfig config) {
        if (instance != null) {
            throw new ApplicationAlreadyCreatedError();
        }

        instance = new Application(module, config);

        return instance;
    }
}
