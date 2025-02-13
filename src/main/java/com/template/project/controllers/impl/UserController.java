package com.template.project.controllers.impl;

import com.template.project.models.dtos.UserCreationDto;
import com.template.project.models.dtos.UserDto;
import com.template.project.models.entities.User;
import com.template.project.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<UserDto>> getUsers() {
    List<UserDto> users = userService.getAll()
        .stream()
        .map(UserDto::fromEntity)
        .toList();

    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
    User user = userService.findById(id);
    UserDto userDto = UserDto.fromEntity(user);

    return ResponseEntity.ok(userDto);
  }

  @PostMapping
  public ResponseEntity<UserDto> createUser(@RequestBody UserCreationDto userCreationDto) {
    User createdUser = userService.create(userCreationDto.toEntity());
    UserDto createdUserDto = UserDto.fromEntity(createdUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
  }
}
