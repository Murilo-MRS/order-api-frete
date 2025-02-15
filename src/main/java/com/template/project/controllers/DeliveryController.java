package com.template.project.controllers;

import com.template.project.exceptions.DeliveryNotFoundException;
import com.template.project.exceptions.UserNotFoundException;
import com.template.project.models.dtos.DeliveryCreationDto;
import com.template.project.models.dtos.DeliveryDto;
import com.template.project.models.dtos.DeliveryUpdateDto;
import com.template.project.models.dtos.DeliveryUpdateStatusDto;
import com.template.project.models.dtos.UserDeliveryDto;
import com.template.project.models.entities.Delivery;
import com.template.project.service.DeliveryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
  private final DeliveryService deliveryService;

  @Autowired
  public DeliveryController(DeliveryService deliveryService) {
    this.deliveryService = deliveryService;
  }

  @GetMapping
  public ResponseEntity<List<DeliveryDto>> getDeliveries() {
    List<Delivery> deliveries = deliveryService.getAll();
    List<DeliveryDto> deliveryDtos = deliveries.stream()
        .map(DeliveryDto::fromEntity)
        .toList();

    return ResponseEntity.ok(deliveryDtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DeliveryDto> getDelivery(@PathVariable Long id) throws DeliveryNotFoundException {
    DeliveryDto delivery = DeliveryDto.fromEntity(deliveryService.findById(id));

    return ResponseEntity.ok(delivery);
  }

  @PostMapping
  public ResponseEntity<DeliveryDto> createDelivery(@Valid @RequestBody DeliveryCreationDto deliveryCreationDto) {
    Delivery createdDelivery = deliveryService.create(deliveryCreationDto.toEntity());
    DeliveryDto createdDeliveryDto = DeliveryDto.fromEntity(createdDelivery);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdDeliveryDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DeliveryDto> updateDelivery(@PathVariable Long id, @Valid @RequestBody DeliveryUpdateDto deliveryUpdateDto)
      throws DeliveryNotFoundException, UserNotFoundException {
    Delivery updatedDelivery = deliveryService.update(id,  deliveryUpdateDto.userId(), deliveryUpdateDto.toEntity());
    DeliveryDto updatedDeliveryDto = DeliveryDto.fromEntity(updatedDelivery);

    return ResponseEntity.ok(updatedDeliveryDto);
  }

  @PutMapping("/{id}/status")
  public ResponseEntity<DeliveryDto> updateDeliveryStatus(@PathVariable Long id,
      @Valid @RequestBody DeliveryUpdateStatusDto updateStatusDto)
      throws DeliveryNotFoundException {

    Delivery updatedDelivery = deliveryService.updateDeliveryStatus(id, updateStatusDto.toEntity()
        .getStatus());
    DeliveryDto updatedDeliveryDto = DeliveryDto.fromEntity(updatedDelivery);

    return ResponseEntity.ok(updatedDeliveryDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) throws DeliveryNotFoundException {
    deliveryService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
