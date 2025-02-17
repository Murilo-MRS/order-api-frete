package com.template.project.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginDto(
    @NotNull(message = "Email is required")
    @Email(
        regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}",
        message = "Email must be valid"
    )
    String email,
    @NotNull(message = "Password is required")
    String password
) {
}
