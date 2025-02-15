package com.template.project.service.impl;

import com.template.project.exceptions.DeliveryNotFoundException;
import com.template.project.exceptions.UserNotFoundException;
import com.template.project.models.entities.Delivery;
import com.template.project.models.entities.User;
import com.template.project.models.enums.DeliveryStatus;
import com.template.project.models.repositories.DeliveryRepository;
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
  private final UserService userService;

  @Autowired
  public DeliveryServiceImpl(DeliveryRepository deliveryRepository, UserService userService) {
    this.deliveryRepository = deliveryRepository;
    this.userService = userService;
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
    List<Delivery> deliveries = deliveryRepository.findAll();
    return deliveries;
  }

  @Override
  public Delivery create(Delivery delivery) {
    delivery.setUser(null);
    return deliveryRepository.save(delivery);
  }

  @Override
  public Delivery update(Long id, Long userId, Delivery delivery)
      throws DeliveryNotFoundException, UserNotFoundException {
    Delivery dataBaseDelivery = findById(id);
    if (userId == null) {
      dataBaseDelivery.setStatus(delivery.getStatus());
      dataBaseDelivery.setDeliveryDate(delivery.getDeliveryDate());
      dataBaseDelivery.setDescription(delivery.getDescription());
      dataBaseDelivery.setUser(null);

      return deliveryRepository.save(dataBaseDelivery);
    }

    User dataBaseUser = userService.findById(userId);

    dataBaseDelivery.setStatus(delivery.getStatus());
    dataBaseDelivery.setDeliveryDate(delivery.getDeliveryDate());
    dataBaseDelivery.setDescription(delivery.getDescription());
    dataBaseDelivery.setUser(dataBaseUser);

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
