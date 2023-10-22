package com.playground.user.module;

import com.beehive.annotations.Module;
import com.playground.user.service.UserService;

@Module(
  services = {
    UserService.class
  }
)
public class UserModule {
}
