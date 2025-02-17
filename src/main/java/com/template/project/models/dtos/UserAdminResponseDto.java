package com.template.project.models.dtos;

import com.template.project.models.entities.User;
import java.time.LocalDateTime;

public record UserAdminResponseDto(
    Long id,
    String name,
    String email,
    String role,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
  public static UserAdminResponseDto fromEntity(User user) {
    return new UserAdminResponseDto(user.getId(),
        user.getName(),
        user.getEmail(),
        user.getRole().name(),
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
  }
}
