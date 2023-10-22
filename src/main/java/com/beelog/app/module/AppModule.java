package com.beelog.app.module;

import com.beehive.annotations.Module;
import com.beelog.auth.module.AuthModule;
import com.beelog.user.module.UserModule;

@Module(
  modules = {
    UserModule.class,
    AuthModule.class
  }
)
public class AppModule {
}
