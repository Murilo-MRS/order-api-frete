package com.template.project.models.dtos;

import com.template.project.models.enums.DeliveryStatus;
import com.template.project.models.enums.validations.ValueOfEnum;
import jakarta.validation.constraints.NotNull;

public record DeliveryUpdateStatusDto (
    @NotNull(message = "Status is required")
    @ValueOfEnum(enumClass = DeliveryStatus.class, message = "Status must be valid")
    DeliveryStatus status
) {
}