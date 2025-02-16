package com.template.project.service.impl;

import com.template.project.exceptions.AccessDeniedException;
import com.template.project.exceptions.UserAlreadyExistsException;
import com.template.project.exceptions.UserNotFoundException;
import com.template.project.models.entities.User;
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
  private final TokenService tokenService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, TokenService tokenService) {
    this.userRepository = userRepository;
    this.tokenService = tokenService;
  }

  @Override
  public User findById(Long id) throws UserNotFoundException {
    Optional<User> user = userRepository.findById(id);
    if (!user.isPresent()) {
      throw new UserNotFoundException();
    }

    return user.get();
  }

  @Override
  public User findById(Long id, String token) throws UserNotFoundException, AccessDeniedException {
    String usernameFromToken = tokenService.validateToken(token);
    String userRoleFromToken = tokenService.getRoleFromToken(token);

    if (userRoleFromToken.equals("ADMIN")) {
      return findById(id);
    }

    User userFromDatabase = findById(id);

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
  public User update(Long id, User user, String token)
      throws UserNotFoundException, UserAlreadyExistsException, AccessDeniedException {
    User userFromDatabase = findById(id);
    boolean isEmailInUse = userRepository.findByEmail(user.getEmail()).isPresent();
    boolean isEmailSameAsBefore = userFromDatabase.getEmail().equals(user.getEmail());

    String usernameFromToken = tokenService.validateToken(token);
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
  public void delete(Long id, String token) throws UserNotFoundException, AccessDeniedException {
    User userFromDatabase = findById(id);
    String usernameFromToken = tokenService.validateToken(token);
    boolean isUserSameAsToken = userFromDatabase.getEmail().equals(usernameFromToken);
    boolean isUserAdmin = tokenService.getRoleFromToken(token).equals("ADMIN");

    if (isUserAdmin) {
      userRepository.deleteById(id);
      return;
    }

    if (!isUserSameAsToken) {
      throw new AccessDeniedException("Access denied");
    }

    userRepository.deleteById(id);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(email));
  }
}
