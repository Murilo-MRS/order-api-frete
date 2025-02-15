package com.template.project.controllers;

import com.template.project.models.dtos.LoginDto;
import com.template.project.models.dtos.TokenDto;
import com.template.project.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
    UsernamePasswordAuthenticationToken usernamePassword =
        new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());
    Authentication authenticate = authenticationManager.authenticate(usernamePassword);
    String token = tokenService.generateToken(authenticate.getName());

    return ResponseEntity.ok(new TokenDto(token));
  }
}
