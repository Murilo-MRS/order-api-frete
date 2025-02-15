package com.template.project.service;

import com.template.project.exceptions.DeliveryNotFoundException;
import com.template.project.exceptions.UserNotFoundException;
import com.template.project.models.entities.Delivery;
import com.template.project.models.enums.DeliveryStatus;
import java.util.List;

public interface DeliveryService {
  List<Delivery> getAll();
  Delivery findById(Long id) throws DeliveryNotFoundException;
  Delivery create(Delivery delivery);
  Delivery update(Long id, Long userId, Delivery delivery) throws DeliveryNotFoundException, UserNotFoundException;
  Delivery updateDeliveryStatus(Long id, DeliveryStatus status) throws DeliveryNotFoundException;
  void delete(Long id) throws DeliveryNotFoundException;
}
