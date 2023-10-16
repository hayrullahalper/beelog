package com.playground.auth.module;

import com.beehive.annotations.Module;
import com.playground.auth.controller.AuthController;
import com.playground.auth.service.AuthService;
import com.playground.user.service.UserService;

@Module(
    services = { AuthService.class, UserService.class },
    controllers = { AuthController.class }
)
public class AuthModule {}
