package com.playground;

import com.beehive.lib.Application.Application;
import com.beehive.lib.Application.ApplicationConfig;
import com.beehive.lib.Factory.Factory;
import com.playground.app.module.AppModule;

public class Main {

  public static void main(String[] args) {
    ApplicationConfig config = new ApplicationConfig("com.playground", "playground");

    Application app = Factory.create(AppModule.class, config);

    app.listen(8080);
  }
}
