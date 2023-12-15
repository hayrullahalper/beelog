package com.beelog.user.service;

public class UserEmailAlreadyExistException extends Exception {

  public UserEmailAlreadyExistException() {
    super("User email already exists");
  }
}
