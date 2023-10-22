package com.playground.app.module;

import com.beehive.annotations.Module;
import com.playground.auth.module.AuthModule;
import com.playground.user.module.UserModule;

@Module(
  modules = {
    UserModule.class,
    AuthModule.class
  }
)
public class AppModule {
}
