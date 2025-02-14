package com.template.project.models.enums;

public enum DeliveryStatus {
  PENDING("PENDING"),
  IN_TRANSIT("IN_TRANSIT"),
  DELIVERED("DELIVERED");

  private final String value;

  private DeliveryStatus(String value) {
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
