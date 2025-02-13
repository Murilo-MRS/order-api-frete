package com.template.project.exceptions;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException() {
    super("No user found");
  }
}
