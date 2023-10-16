package com.playground;

import com.playground.app.module.AppModule;
import com.beehive.lib.Factory.Factory;
import com.beehive.lib.Application.Application;
import com.beehive.lib.Application.ApplicationConfig;

public class Main {

    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig("com.playground", "playground");

        Application app = Factory.create(AppModule.class, config);

        app.listen(8080);
    }
}
