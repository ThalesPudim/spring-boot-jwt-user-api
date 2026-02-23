package com.thales.JavaAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
  
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .error("Business Error")
        .message(ex.getMessage())
        .build();

    return ResponseEntity.badRequest().body(errorResponse);

  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleEmailExists(EmailAlreadyExistsException ex) {

      ErrorResponse error = ErrorResponse.builder()
              .timestamp(LocalDateTime.now())
              .status(HttpStatus.BAD_REQUEST.value())
              .error("Email already exists")
              .message(ex.getMessage())
              .build();

      return ResponseEntity.badRequest().body(error);
  }
  
}
