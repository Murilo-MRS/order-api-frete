package com.template.project.models.dtos;

import com.template.project.models.entities.User;
import com.template.project.models.enums.Role;

public record UserCreationDto(String name, String email, String password, Role role) {
  public User toEntity() {
    return new User(null, name, email, password, role);
  }
}
