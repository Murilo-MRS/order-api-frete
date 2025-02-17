package com.template.project.exceptions;

public class DeliveryNotFoundException extends NotFoundException {
  public DeliveryNotFoundException() {
    super("No delivery found");
  }
}
