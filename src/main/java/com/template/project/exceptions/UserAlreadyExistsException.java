package com.template.project.exceptions;

public class UserAlreadyExistsException extends Exception {

  public UserAlreadyExistsException() {
    super("User already exists");
  }
}
