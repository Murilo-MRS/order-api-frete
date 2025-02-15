package com.template.project.service;

import java.time.Instant;

public interface TokenService {

  public String generateToken(String username);

  public Instant generateExpiration();

  public String validateToken(String token);
}