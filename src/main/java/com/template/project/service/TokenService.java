package com.template.project.service;

import com.template.project.models.enums.Role;
import java.time.Instant;

public interface TokenService {
  String generateToken(String username, String role);
  String getRoleFromToken(String token);
  Instant generateExpiration();
  String validateToken(String token);
}