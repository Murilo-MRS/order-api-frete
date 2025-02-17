package com.template.project.models.dtos;

import com.template.project.models.entities.User;
import java.time.LocalDateTime;

public record UserDeliveryDto(
    Long id,
    String name,
    String email
    ) {
  public static UserDeliveryDto fromEntity(User user) {
    return new UserDeliveryDto(
        user.getId(),
        user.getName(),
        user.getEmail()
    );
  }
}
