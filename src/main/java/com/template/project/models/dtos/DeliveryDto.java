package com.template.project.models.dtos;

import com.template.project.models.entities.Delivery;
import java.time.LocalDateTime;

public record DeliveryDto(
    Long id,
    String description,
    String status,
    Long userId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
  public static DeliveryDto fromEntity(Delivery delivery) {
    return new DeliveryDto(
        delivery.getId(),
        delivery.getDescription(),
        delivery.getStatus().name(),
        delivery.getUser().getId(),
        delivery.getCreatedAt(),
        delivery.getUpdatedAt()
    );
  }
}
