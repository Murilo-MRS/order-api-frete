package com.template.project.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.template.project.service.TokenService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
  private final Algorithm algorithm;

  public TokenServiceImpl(@Value("${api.security.token.secret}") String secret) {
    this.algorithm = Algorithm.HMAC256(secret);
  }

  @Override
  public String generateToken(String username) {
    return JWT.create()
        .withSubject(username)
        .withExpiresAt(generateExpiration())
        .sign(algorithm);
  }

  @Override
  public Instant generateExpiration() {
    return Instant.now()
        .plus(2, ChronoUnit.HOURS);
  }

  @Override
  public String validateToken(String token) {
    return JWT.require(algorithm)
        .build()
        .verify(token)
        .getSubject();
  }
}
