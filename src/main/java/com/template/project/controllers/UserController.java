package com.template.project.controllers;

import com.template.project.exceptions.AccessDeniedException;
import com.template.project.exceptions.DeliveryNotFoundException;
import com.template.project.exceptions.UserAlreadyExistsException;
import com.template.project.exceptions.UserNotFoundException;
import com.template.project.models.dtos.UserAdminResponseDto;
import com.template.project.models.dtos.UserCreationDto;
import com.template.project.models.dtos.UserDeliveryListDto;
import com.template.project.models.dtos.UserDto;
import com.template.project.models.dtos.UserUpdateDto;
import com.template.project.models.entities.User;
import com.template.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários")
public class UserController {
  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  @Operation(summary = "Listar usuários")
  @SecurityRequirement(name = "Bearer Authentication")
  public ResponseEntity<List<UserAdminResponseDto>> getUsers() {
    List<UserAdminResponseDto> users = userService.getAll()
        .stream()
        .map(UserAdminResponseDto::fromEntity)
        .toList();

    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar usuário por ID")
  @SecurityRequirement(name = "Bearer Authentication")
  public ResponseEntity<UserDto> getUser(
      @PathVariable Long id,
      @RequestHeader("Authorization") String authorizationHeader
      ) throws UserNotFoundException, AccessDeniedException {
    String token = authorizationHeader.replace("Bearer ", "");
    User user = userService.findById(id);
    UserDto userDto = UserDto.fromEntity(user);

    return ResponseEntity.ok(userDto);
  }

  @PostMapping
  @Operation(summary = "Criar usuário")
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreationDto userCreationDto)
      throws UserAlreadyExistsException {
    User createdUser = userService.create(userCreationDto.toEntity());
    UserDto createdUserDto = UserDto.fromEntity(createdUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Atualizar usuário")
  @SecurityRequirement(name = "Bearer Authentication")
  public ResponseEntity<UserDto> updateUser(
      @PathVariable Long id,
      @Valid @RequestBody UserUpdateDto userUpdateDto,
      @RequestHeader("Authorization") String authorizationHeader
  ) throws UserNotFoundException, UserAlreadyExistsException, AccessDeniedException {
    String token = authorizationHeader.replace("Bearer ", "");

    User updatedUser = userService.update(id, userUpdateDto.toEntity());
    UserDto updatedUserDto = UserDto.fromEntity(updatedUser);
    return ResponseEntity.ok(updatedUserDto);
  }

  @PostMapping("/{id}/delivery/{deliveryId}")
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  @Operation(summary = "Adicionar entrega ao usuário")
  @SecurityRequirement(name = "Bearer Authentication")
  public ResponseEntity<UserDeliveryListDto> addDelivery(@PathVariable Long id, @PathVariable Long deliveryId) throws UserNotFoundException, DeliveryNotFoundException {
    User user = userService.addDelivery(id, deliveryId);
    UserDeliveryListDto userDeliveryListDto = UserDeliveryListDto.fromEntity(user);
    return ResponseEntity.ok(userDeliveryListDto);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deletar usuário")
  @SecurityRequirement(name = "Bearer Authentication")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) throws UserNotFoundException, AccessDeniedException {
    String token = authorizationHeader.replace("Bearer ", "");
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
