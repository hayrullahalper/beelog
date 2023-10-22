package com.beelog.user.module;

import com.beehive.annotations.Module;
import com.beelog.user.service.UserService;

@Module(
  services = {
    UserService.class
  }
)
public class UserModule {
}
