package com.template.project.controllers;

import com.template.project.exceptions.DeliveryNotFoundException;
import com.template.project.models.dtos.DeliveryCreationDto;
import com.template.project.models.dtos.DeliveryDto;
import com.template.project.models.dtos.DeliveryUpdateStatusDto;
import com.template.project.models.entities.Delivery;
import com.template.project.service.DeliveryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    List<DeliveryDto> deliveries = deliveryService.getAll()
        .stream()
        .map(DeliveryDto::fromEntity)
        .toList();

    return ResponseEntity.ok(deliveries);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DeliveryDto> getDelivery(@PathVariable Long id) throws DeliveryNotFoundException {
    DeliveryDto delivery = DeliveryDto.fromEntity(deliveryService.findById(id));

    return ResponseEntity.ok(delivery);
  }

  @PostMapping
  public ResponseEntity<DeliveryDto> createDelivery(@RequestBody DeliveryCreationDto deliveryCreationDto) {
    Delivery createdDelivery = deliveryService.create(deliveryCreationDto.toEntity());
    DeliveryDto createdDeliveryDto = DeliveryDto.fromEntity(createdDelivery);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdDeliveryDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DeliveryDto> updateDelivery(@PathVariable Long id, @RequestBody DeliveryCreationDto deliveryUpdateDto)
      throws DeliveryNotFoundException {
    Delivery updatedDelivery = deliveryService.update(id, deliveryUpdateDto.toEntity());
    DeliveryDto updatedDeliveryDto = DeliveryDto.fromEntity(updatedDelivery);

    return ResponseEntity.ok(updatedDeliveryDto);
  }

  @PutMapping("/{id}/status")
  public ResponseEntity<DeliveryDto> updateDeliveryStatus(@PathVariable Long id,
      @RequestBody DeliveryUpdateStatusDto updateStatusDto)
      throws DeliveryNotFoundException {
    Delivery updatedDelivery = deliveryService.updateDeliveryStatus(id, updateStatusDto.status());
    DeliveryDto updatedDeliveryDto = DeliveryDto.fromEntity(updatedDelivery);

    return ResponseEntity.ok(updatedDeliveryDto);
  }
}
