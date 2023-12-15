package com.beelog.user.service;

public class UsernameAlreadyExistException extends Exception {
  public UsernameAlreadyExistException() {
    super("Username already exists");
  }
}
