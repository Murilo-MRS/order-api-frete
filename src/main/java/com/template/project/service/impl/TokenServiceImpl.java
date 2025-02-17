package com.template.project.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.template.project.exceptions.UserNotFoundException;
import com.template.project.models.entities.User;
import com.template.project.models.repositories.UserRepository;
import com.template.project.service.TokenService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

  private final Algorithm algorithm;
  private final UserRepository userRepository;

  public TokenServiceImpl(@Value("${api.security.token.secret}") String secret,
      UserRepository userRepository) {
    this.algorithm = Algorithm.HMAC256(secret);
    this.userRepository = userRepository;
  }


  @Override
  public String generateToken(String username, String role) {
    return JWT.create()
        .withSubject(username)
        .withClaim("role", role)
        .withExpiresAt(generateExpiration())
        .sign(algorithm);
  }

  @Override
  public String getRoleFromToken() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().iterator().next().getAuthority();
  }

  @Override
  public User getUser() throws UserNotFoundException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByEmail(authentication.getName())
        .orElseThrow(UserNotFoundException::new);
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
