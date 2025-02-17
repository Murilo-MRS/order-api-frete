package com.template.project.models.dtos;

import com.template.project.models.entities.Delivery;
import com.template.project.models.enums.DeliveryStatus;
import com.template.project.models.enums.validations.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DeliveryCreationDto(
    @NotNull(message = "Description is required")
    @NotBlank(message = "Description not be blank")
    @Size(min = 4, max = 255, message = "Description must be between 4 and 255 characters")
    String description,
    @NotNull(message = "Status is required")
    @ValueOfEnum(enumClass = DeliveryStatus.class, message = "Status must be valid")
    String status
) {
    public Delivery toEntity() {
        return new Delivery(
            null,
            description,
            null,
            DeliveryStatus.valueOf(status)
        );
    }
}
