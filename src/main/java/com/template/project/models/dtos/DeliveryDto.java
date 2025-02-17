package com.template.project.models.dtos;

import com.template.project.models.entities.Delivery;
import java.time.LocalDateTime;

public record DeliveryDto(
    Long id,
    String description,
    String status,
    LocalDateTime deliveryDate,
    UserDeliveryDto user,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
  public DeliveryDto(
      Long id,
      String description,
      String status,
      LocalDateTime deliveryDate,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this(id, description, status, deliveryDate, null, createdAt, updatedAt);
  }

  public static DeliveryDto fromEntity(Delivery delivery) {
    if (delivery.getUser() != null) {
      return new DeliveryDto(
          delivery.getId(),
          delivery.getDescription(),
          delivery.getStatus().name(),
          delivery.getDeliveryDate(),
          new UserDeliveryDto(delivery.getUser().getId(), delivery.getUser().getName(), delivery.getUser().getEmail()),
          delivery.getCreatedAt(),
          delivery.getUpdatedAt()
      );
    }
    return new DeliveryDto(
        delivery.getId(),
        delivery.getDescription(),
        delivery.getStatus().name(),
        delivery.getDeliveryDate(),
       null,
        delivery.getCreatedAt(),
        delivery.getUpdatedAt()
    );
  }
}
