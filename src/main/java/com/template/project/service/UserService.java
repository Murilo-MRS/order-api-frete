package com.template.project.service;

import com.template.project.exceptions.AccessDeniedException;
import com.template.project.exceptions.UserAlreadyExistsException;
import com.template.project.exceptions.UserNotFoundException;
import com.template.project.models.entities.User;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  User findById(Long id) throws UserNotFoundException;
  User findByEmail(String email) throws UserNotFoundException;
  List<User> getAll();
  User create(User user) throws UserAlreadyExistsException;
  User update(Long id, User user, String token)
      throws UserNotFoundException, UserAlreadyExistsException, AccessDeniedException;
  void delete(Long id) throws UserNotFoundException;
}
