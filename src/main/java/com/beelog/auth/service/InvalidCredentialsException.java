package com.beelog.auth.service;

public class InvalidCredentialsException extends Exception {
  public InvalidCredentialsException() {
    super("Invalid credentials");
  }
}
