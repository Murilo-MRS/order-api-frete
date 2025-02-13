package com.template.project.service;

import com.template.project.models.entities.Delivery;
import com.template.project.models.enums.DeliveryStatus;
import java.util.List;

public interface DeliveryService {
  List<Delivery> getAll();
  Delivery findById(Long id);
  Delivery create(Delivery delivery);
  Delivery update(Delivery delivery);
  void delete(Long id);
}
