package com.template.project.models.dtos;

import com.template.project.models.entities.User;
import java.util.List;

public record UserDeliveryListDto(
    Long id,
    String name,
    String email,
    List<DeliveryUserDto> deliveries
) {
  public static UserDeliveryListDto fromEntity(User user) {
    return new UserDeliveryListDto(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getDeliveries().stream().map(DeliveryUserDto::fromEntity).toList()
    );
  }
}
