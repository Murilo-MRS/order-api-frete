package com.template.project.models.dtos;

import com.template.project.models.entities.User;
import java.time.LocalDateTime;

public record UserDto(
    Long id,
    String name,
    String email,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
    ) {
  public static UserDto fromEntity(User user) {
    return new UserDto(user.getId(),
        user.getName(),
        user.getEmail(),
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
  }
}
