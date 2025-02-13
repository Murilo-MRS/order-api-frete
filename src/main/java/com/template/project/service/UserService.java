package com.template.project.service;

import com.template.project.models.entities.User;
import java.util.List;

public interface UserService {
  User findById(Long id);
  User findByEmail(String email);
  List<User> getAll();
  User create(User user);
  User update(User user);
  void delete(Long id);
}
