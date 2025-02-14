package com.template.project.models.enums;

public enum Role {
  ADMIN("ADMIN"),
  USER("USER");
  private final String value;

  private Role(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
