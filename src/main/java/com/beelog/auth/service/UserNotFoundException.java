package com.beelog.auth.service;

public class UserNotFoundException extends Exception {
  public UserNotFoundException(String username) {
    super("User not found: " + username);
  }
}
