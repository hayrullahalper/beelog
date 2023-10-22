package com.beelog.auth.controller;

import javax.validation.constraints.NotNull;

class LoginRequest {
  @NotNull
  public String username;

  @NotNull
  public String password;
}
