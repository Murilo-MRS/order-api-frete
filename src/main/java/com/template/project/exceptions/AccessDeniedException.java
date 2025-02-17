package com.template.project.exceptions;

public class AccessDeniedException extends Exception {
  public AccessDeniedException(String message) {
    super("Access denied");
  }
}
