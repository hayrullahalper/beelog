package com.beelog.user.service;

public class UserNotFoundException extends Exception {
  public UserNotFoundException(String criteria, String value) {
    super("User not found with criteria: " + criteria + " = " + value);
  }
}
