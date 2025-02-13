package com.template.project.models.dtos;

import com.template.project.models.entities.User;

public record UserDto(Long id, String name, String email) {
  public static UserDto fromEntity(User user) {
    return new UserDto(user.getId(), user.getName(), user.getEmail());
  }
}
