package com.template.project.models.dtos;

import com.template.project.models.entities.Delivery;
import com.template.project.models.enums.DeliveryStatus;
import com.template.project.models.enums.validations.ValueOfEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DeliveryCreationDto(
    @NotNull
    @Size(min = 1, max = 255)
    String description,
    @NotNull
    @ValueOfEnum(enumClass = DeliveryStatus.class, message = "Status must be valid")
    DeliveryStatus status
) {
    public Delivery toEntity() {
        return new Delivery(
            null,
            description,
            null,
            status
        );
    }
}
