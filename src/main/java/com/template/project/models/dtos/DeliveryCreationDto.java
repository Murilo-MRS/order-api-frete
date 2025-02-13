package com.template.project.models.dtos;

import com.template.project.models.entities.Delivery;
import com.template.project.models.enums.DeliveryStatus;

public record DeliveryCreationDto(
    String description,
    DeliveryStatus status,
    Long userId
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
