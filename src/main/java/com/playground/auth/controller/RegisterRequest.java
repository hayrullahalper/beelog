package com.playground.auth.controller;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

class RegisterRequest {
  @NotNull
  @Size(min = 2, max = 36)
  public String name;

  @NotNull
  @Size(min = 4, max = 20)
  public String username;

  @Email
  @NotNull
  public String email;

  @NotNull
  @Size(min = 8, max = 32)
  public String password;
}
