package com.template.project.service.impl;

import com.template.project.exceptions.AccessDeniedException;
import com.template.project.exceptions.DeliveryNotFoundException;
import com.template.project.exceptions.UserAlreadyExistsException;
import com.template.project.exceptions.UserNotFoundException;
import com.template.project.models.entities.Delivery;
import com.template.project.models.entities.User;
import com.template.project.models.repositories.DeliveryRepository;
import com.template.project.models.repositories.UserRepository;
import com.template.project.service.TokenService;
import com.template.project.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final DeliveryRepository deliveryRepository;
  private final TokenService tokenService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, DeliveryRepository deliveryRepository, TokenService tokenService) {
    this.userRepository = userRepository;
    this.deliveryRepository = deliveryRepository;
    this.tokenService = tokenService;
  }

  @Override
  public User findById(Long id) throws UserNotFoundException, AccessDeniedException {
    String usernameFromToken = tokenService.getUser().getEmail();
    String userRoleFromToken = tokenService.getRoleFromToken();

    if (userRoleFromToken.equals("ADMIN")) {
      return userRepository.findById(id)
          .orElseThrow(UserNotFoundException::new);
    }

    User userFromDatabase = userRepository.findById(id)
        .orElseThrow(UserNotFoundException::new);

    if (!userFromDatabase.getEmail().equals(usernameFromToken)) {
      throw new AccessDeniedException("Access denied");
    }

    return userFromDatabase;
  }

  @Override
  public User findByEmail(String email) throws UserNotFoundException {
    Optional<User> user = userRepository.findByEmail(email);
    if (!user.isPresent()) {
      throw new UserNotFoundException();
    }

    return user.get();
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public User create(User user) throws UserAlreadyExistsException {
    Optional<User> userFromDatabase = userRepository.findByEmail(user.getEmail());
    if (userFromDatabase.isPresent()) {
      throw new UserAlreadyExistsException();
    }
    String hashedPassword = new BCryptPasswordEncoder()
        .encode(user.getPassword());
    user.setPassword(hashedPassword);
    return userRepository.save(user);
  }

  @Override
  public User update(Long id, User user)
      throws UserNotFoundException, UserAlreadyExistsException, AccessDeniedException {
    User userFromDatabase = userRepository.findById(id)
        .orElseThrow(UserNotFoundException::new);
    boolean isEmailInUse = userRepository.findByEmail(user.getEmail()).isPresent();
    boolean isEmailSameAsBefore = userFromDatabase.getEmail().equals(user.getEmail());

    String usernameFromToken = tokenService.getUser().getEmail();
    if (!userFromDatabase.getEmail().equals(usernameFromToken)) {
      throw new AccessDeniedException("Access denied");
    }

    if (isEmailInUse && !isEmailSameAsBefore) {
      throw new UserAlreadyExistsException();
    }

    User userToUpdate = userFromDatabase;

    userToUpdate.setName(user.getName());
    userToUpdate.setEmail(user.getEmail());
    String hashedPassword = new BCryptPasswordEncoder()
        .encode(user.getPassword());
    userToUpdate.setPassword(hashedPassword);

    return userRepository.save(userToUpdate);
  }

  @Override
  public void delete(Long id) throws UserNotFoundException, AccessDeniedException {
    User userFromDatabase = userRepository.findById(id)
        .orElseThrow(UserNotFoundException::new);
    String usernameFromToken = tokenService.getUser().getEmail();
    boolean isUserSameAsToken = userFromDatabase.getEmail().equals(usernameFromToken);
    boolean isUserAdmin = tokenService.getRoleFromToken().equals("ADMIN");

    if (isUserAdmin) {
      userRepository.deleteById(id);
      return;
    }

    if (!isUserSameAsToken) {
      throw new AccessDeniedException("Access denied");
    }

    userRepository.deleteById(id);
  }

  public User addDelivery(Long userId, Long deliveryId) throws UserNotFoundException, DeliveryNotFoundException {
    User user = userRepository.findById(userId)
        .orElseThrow(UserNotFoundException::new);
    Delivery delivery = deliveryRepository.findById(deliveryId)
        .orElseThrow(DeliveryNotFoundException::new);

    user.addDelivery(delivery);

    return userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(email));
  }
}
