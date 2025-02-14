package com.template.project.service;

import com.template.project.exceptions.UserAlreadyExistsException;
import com.template.project.exceptions.UserNotFoundException;
import com.template.project.models.entities.User;
import java.util.List;

public interface UserService {
  User findById(Long id) throws UserNotFoundException;
  User findByEmail(String email) throws UserNotFoundException;
  List<User> getAll();
  User create(User user) throws UserAlreadyExistsException;
  User update(Long id, User user) throws UserNotFoundException;
  void delete(Long id) throws UserNotFoundException;
}
