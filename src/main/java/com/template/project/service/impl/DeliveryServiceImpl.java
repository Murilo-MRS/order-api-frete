package com.template.project.service.impl;

import com.template.project.exceptions.DeliveryNotFoundException;
import com.template.project.models.entities.Delivery;
import com.template.project.models.enums.DeliveryStatus;
import com.template.project.models.repositories.DeliveryRepository;
import com.template.project.models.repositories.UserRepository;
import com.template.project.service.DeliveryService;
import com.template.project.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
  public Delivery findById(Long id) throws DeliveryNotFoundException {
    Optional<Delivery> delivery = deliveryRepository.findById(id);

    if (!delivery.isPresent()) {
      throw new DeliveryNotFoundException();
    }

    return delivery.get();
  }

  @Override
  public List<Delivery> getAll() {
    return List.of();
  }

  @Override
  public Delivery update(Long id, Delivery delivery) throws DeliveryNotFoundException {
    Delivery dataBaseDelivery = findById(id);

    dataBaseDelivery.setStatus(delivery.getStatus());
    dataBaseDelivery.setDeliveryDate(delivery.getDeliveryDate());
    dataBaseDelivery.setDescription(delivery.getDescription());
    dataBaseDelivery.setUser(delivery.getUser());

    return deliveryRepository.save(dataBaseDelivery);
  }

  //TODO: role = 'USER' should update only his own deliveries and role = 'ADMIN' should update all deliveries
  @Override
  public Delivery updateDeliveryStatus(Long id, DeliveryStatus status) throws DeliveryNotFoundException {
    Delivery dataBaseDelivery = findById(id);

    if (dataBaseDelivery.getStatus() == status) {
      return dataBaseDelivery;
    }

    if (status == DeliveryStatus.DELIVERED) {
      LocalDateTime deliveryDate = LocalDateTime.now();

      dataBaseDelivery.setDeliveryDate(deliveryDate);
      dataBaseDelivery.setStatus(status);

      return deliveryRepository.save(dataBaseDelivery);
    }

    dataBaseDelivery.setStatus(status);

    return deliveryRepository.save(dataBaseDelivery);
  }

  @Override
  public void delete(Long id) throws DeliveryNotFoundException {
    Delivery delivery = findById(id);
    deliveryRepository.deleteById(delivery.getId());
  }
}
