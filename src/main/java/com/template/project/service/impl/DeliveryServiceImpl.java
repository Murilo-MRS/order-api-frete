package com.template.project.service.impl;

import com.template.project.exceptions.AccessDeniedException;
import com.template.project.exceptions.DeliveryNotFoundException;
import com.template.project.exceptions.UserNotFoundException;
import com.template.project.models.entities.Delivery;
import com.template.project.models.entities.User;
import com.template.project.models.enums.DeliveryStatus;
import com.template.project.models.repositories.DeliveryRepository;
import com.template.project.service.DeliveryService;
import com.template.project.service.TokenService;
import com.template.project.service.UserService;
import java.time.LocalDateTime;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {
  private final DeliveryRepository deliveryRepository;
  private final UserService userService;
  private final TokenService tokenService;

  @Autowired
  public DeliveryServiceImpl(DeliveryRepository deliveryRepository, UserService userService, TokenService tokenService) {
    this.deliveryRepository = deliveryRepository;
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @Override
  public Delivery findById(Long id)
      throws DeliveryNotFoundException {

    Optional<Delivery> delivery = deliveryRepository.findById(id);

    if (!delivery.isPresent()) {
      throw new DeliveryNotFoundException();
    }

    return delivery.get();
  }

  @Override
  public Delivery findById(Long id, String token)
      throws DeliveryNotFoundException, AccessDeniedException {
    String usernameFromToken = tokenService.validateToken(token);
    String userRoleFromToken = tokenService.getRoleFromToken(token);

    if (userRoleFromToken.equals("ADMIN")) {
      return findById(id);
    }
    Delivery delivery = findById(id);

    boolean isDeliveryOwner = delivery.getUser() != null && delivery.getUser().getEmail().equals(usernameFromToken);

    if (!isDeliveryOwner) {
      throw new AccessDeniedException("Access denied");
    }

    return delivery;
  }

  @Override
  public List<Delivery> getAll(String token) throws AccessDeniedException, UserNotFoundException {
    boolean isAdmin = tokenService.getRoleFromToken(token).equals("ADMIN");
    String usernameFromToken = tokenService.validateToken(token);

    if (isAdmin) {
      return deliveryRepository.findAll();
    }

    User userFromDatabase = userService.findByEmail(usernameFromToken);
    List<Delivery> deliveries = deliveryRepository.findByUserId(userFromDatabase.getId());

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
    Delivery dataBaseDelivery = deliveryRepository.findById(id)
        .orElseThrow(DeliveryNotFoundException::new);

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

  private Delivery handleUpdateDeliveryStatus(Delivery delivery, DeliveryStatus status) {
    if (delivery.getStatus() == status) {
      return delivery;
    }
    if (status == DeliveryStatus.DELIVERED) {
      LocalDateTime deliveryDate = LocalDateTime.now();
      delivery.setDeliveryDate(deliveryDate);
      delivery.setStatus(status);
      return deliveryRepository.save(delivery);
    }
    delivery.setStatus(status);
    delivery.setDeliveryDate(null);
    return deliveryRepository.save(delivery);
  }

  @Override
  public Delivery updateDeliveryStatus(Long id, DeliveryStatus status, String token)
      throws DeliveryNotFoundException, AccessDeniedException {
    String usernameFromToken = tokenService.validateToken(token);
    boolean isAdmin = tokenService.getRoleFromToken(token).equals("ADMIN");
    Delivery delivery = findById(id);
    boolean isDeliveryOwner = delivery.getUser() != null && delivery.getUser().getEmail().equals(usernameFromToken);

    if (isAdmin) {
      return handleUpdateDeliveryStatus(delivery, status);
    }

    if (!isDeliveryOwner) {
      throw new AccessDeniedException("Access denied");
    }

    if(delivery.getStatus() == DeliveryStatus.DELIVERED) {
      throw new AccessDeniedException("Access denied");
    }

    if(delivery.getStatus() == DeliveryStatus.IN_TRANSIT && status != DeliveryStatus.DELIVERED) {
      throw new AccessDeniedException("Access denied");
    }

    if (delivery.getStatus() == DeliveryStatus.PENDING && status != DeliveryStatus.IN_TRANSIT) {
      throw new AccessDeniedException("Access denied");
    }

    return handleUpdateDeliveryStatus(delivery, status);
  }

  @Override
  public void delete(Long id) throws DeliveryNotFoundException {
    Delivery delivery = findById(id);
    deliveryRepository.deleteById(delivery.getId());
  }
}
