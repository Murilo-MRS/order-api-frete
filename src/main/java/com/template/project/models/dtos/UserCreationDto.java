package com.template.project.models.dtos;

import com.template.project.models.entities.User;
import com.template.project.models.enums.Role;
import com.template.project.models.enums.validations.ValueOfEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreationDto(
    @NotNull(message = "Name is required")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    String name,
    @NotNull(message = "Email is required")
    @Email(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}",
        message = "Email must be valid")
    String email,
    @NotNull(message = "Password is required")
    @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
    String password,
    @NotNull(message = "Role is required")
    @ValueOfEnum(enumClass = Role.class, message = "Role must be valid")
    String role
) {
  public User toEntity() {
    return new User(null, name, email, password, Role.valueOf(role));
  }
}
