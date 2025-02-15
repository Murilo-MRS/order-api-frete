package com.template.project.service.impl;

import com.template.project.exceptions.UserAlreadyExistsException;
import com.template.project.exceptions.UserNotFoundException;
import com.template.project.models.entities.User;
import com.template.project.models.repositories.UserRepository;
import com.template.project.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
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
  public User update(Long id, User user) throws UserNotFoundException, UserAlreadyExistsException {
    User userFromDatabase = findById(id);
    Optional<User> checkUserEmail = userRepository.findByEmail(user.getEmail());
    if (checkUserEmail.isPresent()) {
      throw new UserAlreadyExistsException();
    }

    User userToUpdate = userFromDatabase;

    userToUpdate.setName(user.getName());
    userToUpdate.setEmail(user.getEmail());
    userToUpdate.setPassword(user.getPassword());
    userToUpdate.setRole(user.getRole());

    return userRepository.save(userToUpdate);
  }

  @Override
  public void delete(Long id) throws UserNotFoundException {
    Optional<User> userFromDatabase = userRepository.findById(id);
    if (!userFromDatabase.isPresent()) {
      throw new UserNotFoundException();
    }
    userRepository.deleteById(id);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(email));

  }
}
