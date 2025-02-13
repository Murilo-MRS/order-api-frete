package com.template.project.service.impl;

import com.template.project.models.entities.User;
import com.template.project.models.repositories.UserRepository;
import com.template.project.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User findById(Long id) {
    return null;
  }

  @Override
  public User findByEmail(String email) {
    return null;
  }

  @Override
  public List<User> getAll() {
    return List.of();
  }

  @Override
  public User create(User user) {
    return null;
  }

  @Override
  public User update(User user) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }
}
