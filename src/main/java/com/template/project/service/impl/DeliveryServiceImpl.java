package com.template.project.service.impl;

import com.template.project.models.entities.Delivery;
import com.template.project.models.repositories.DeliveryRepository;
import com.template.project.models.repositories.UserRepository;
import com.template.project.service.DeliveryService;
import com.template.project.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {
  private final DeliveryRepository deliveryRepository;
  private final UserRepository userRepository;

  @Autowired
  public DeliveryServiceImpl(DeliveryRepository deliveryRepository, UserRepository userRepository) {
    this.deliveryRepository = deliveryRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Delivery create(Delivery delivery) {
    return null;
  }

  @Override
  public Delivery findById(Long id) {
    return null;
  }

  @Override
  public List<Delivery> getAll() {
    return List.of();
  }

  @Override
  public Delivery update(Delivery delivery) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }
}
