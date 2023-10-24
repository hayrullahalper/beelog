package com.beelog;

import com.beehive.lib.application.BeehiveApplicationFactory;
import com.beehive.lib.application.BeehiveConfig;
import com.beelog.app.module.AppModule;

public class Main {

  public static void main(String[] args) {
    var config = BeehiveConfig.create("com.beelog", "beelog");

    var app = BeehiveApplicationFactory.create(AppModule.class, config);

    app.listen(8080);
  }
}
