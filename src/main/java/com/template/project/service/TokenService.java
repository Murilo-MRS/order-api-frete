package com.template.project.service;

import com.template.project.exceptions.UserNotFoundException;
import com.template.project.models.entities.User;
import java.time.Instant;

public interface TokenService {
  String generateToken(String username, String role);

  String getRoleFromToken();

  User getUser() throws UserNotFoundException;

  Instant generateExpiration();
  String validateToken(String token);
}