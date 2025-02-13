package com.template.project.models.dtos;

import com.template.project.models.entities.Delivery;

public record DeliveryDto(Long id, String description, String status, Long userId) {
  public static DeliveryDto fromEntity(Delivery delivery) {
    return new DeliveryDto(
        delivery.getId(),
        delivery.getDescription(),
        delivery.getStatus().name(),
        delivery.getUser().getId()
    );
  }
}
