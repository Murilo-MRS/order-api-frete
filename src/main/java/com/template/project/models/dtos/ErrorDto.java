package com.template.project.models.dtos;

import java.util.List;

public record ErrorDto(
    String message,
    List<FieldError> fieldErrors
) {
  public ErrorDto(String message) {
    this(message, null);
  }

  public static record FieldError(String field, String error) {
  }
}