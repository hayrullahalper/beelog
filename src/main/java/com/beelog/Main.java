package com.beelog;

import com.beehive.lib.Application.Application;
import com.beehive.lib.Application.ApplicationConfig;
import com.beehive.lib.Factory.Factory;
import com.beelog.app.module.AppModule;

public class Main {

  public static void main(String[] args) {
    ApplicationConfig config = new ApplicationConfig("com.beelog", "playground");

    Application app = Factory.create(AppModule.class, config);

    app.listen(8080);
  }
}
