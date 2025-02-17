package com.template.project.models.dtos;

import com.template.project.models.entities.Delivery;
import java.time.LocalDateTime;

public record DeliveryUserDto(
    Long id,
    String description,
    String status,
    LocalDateTime deliveryDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
  public static DeliveryUserDto fromEntity(Delivery delivery) {
    return new DeliveryUserDto(
        delivery.getId(),
        delivery.getDescription(),
        delivery.getStatus().name(),
        delivery.getDeliveryDate(),
        delivery.getCreatedAt(),
        delivery.getUpdatedAt()
    );
  }
}
