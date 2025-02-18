package com.template.project.advice;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.template.project.exceptions.AccessDeniedException;
import com.template.project.exceptions.NotFoundException;
import com.template.project.exceptions.UserAlreadyExistsException;
import com.template.project.models.dtos.ErrorDto;
import com.template.project.models.dtos.ErrorDto.FieldError;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerAdvice {
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(exception.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors().stream()
        .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
        .toList();

    return ResponseEntity.badRequest().body(new ErrorDto("Validation failed", fieldErrors));
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorDto> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto(exception.getMessage()));
  }

  @ExceptionHandler({BadCredentialsException.class, TokenExpiredException.class })
  public ResponseEntity<ErrorDto> handleBadCredentialsException(RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto("Invalid username or password"));
  }

  @ExceptionHandler({AuthorizationDeniedException.class, JWTDecodeException.class})
  public ResponseEntity<ErrorDto> handleAuthorizationDeniedException(RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto("Access denied"));
  }
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorDto> handleAuthorizationDeniedException(AccessDeniedException exception) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto("Access denied"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleException(Exception exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("An error occurred"));
  }
}
