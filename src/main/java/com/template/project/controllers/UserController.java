package com.template.project.controllers;

import com.template.project.exceptions.UserAlreadyExistsException;
import com.template.project.exceptions.UserNotFoundException;
import com.template.project.models.dtos.UserCreationDto;
import com.template.project.models.dtos.UserDto;
import com.template.project.models.dtos.UserUpdateDto;
import com.template.project.models.entities.User;
import com.template.project.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  public ResponseEntity<UserDto> getUser(@PathVariable Long id) throws UserNotFoundException {
    User user = userService.findById(id);
    UserDto userDto = UserDto.fromEntity(user);

    return ResponseEntity.ok(userDto);
  }

  @PostMapping
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreationDto userCreationDto)
      throws UserAlreadyExistsException {
    User createdUser = userService.create(userCreationDto.toEntity());
    UserDto createdUserDto = UserDto.fromEntity(createdUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> updateUser(
      @PathVariable Long id,
      @Valid @RequestBody UserUpdateDto userUpdateDto
  ) throws UserNotFoundException, UserAlreadyExistsException {
    User updatedUser = userService.update(id, userUpdateDto.toEntity());
    UserDto updatedUserDto = UserDto.fromEntity(updatedUser);
    return ResponseEntity.ok(updatedUserDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
