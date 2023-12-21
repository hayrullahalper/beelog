package com.beelog.auth.module;

import com.beehive.annotations.Module;
import com.beelog.auth.controller.AuthController;
import com.beelog.auth.service.AuthService;
import com.beelog.auth.service.PasswordService;
import com.beelog.user.service.UserService;

@Module(
  services = {AuthService.class, UserService.class, PasswordService.class},
  controllers = {AuthController.class}
)
public class AuthModule {
}
