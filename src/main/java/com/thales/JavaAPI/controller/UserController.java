package com.thales.JavaAPI.controller;

import com.thales.JavaAPI.dto.UserRequestDTO;
import com.thales.JavaAPI.dto.UserResponseDTO;
import com.thales.JavaAPI.service.UserService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
  
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO dto) {
    return ResponseEntity.ok(userService.createUser(dto));
  }

  @GetMapping
  public List<UserResponseDTO> findAll() {
    return userService.findAllUsers();
  }

  @GetMapping("/{id}")
  public UserResponseDTO findById(@PathVariable Long id) {
    return userService.findUserById(id);
  }

  @PutMapping("/{id}")
  public UserResponseDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
    return userService.updateUser(id, dto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable long id) {
    userService.deleteUser(id);
  }

}
