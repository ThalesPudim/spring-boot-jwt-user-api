package com.thales.JavaAPI.controller;


import com.thales.JavaAPI.dto.*;
import com.thales.JavaAPI.entity.User;
import com.thales.JavaAPI.repository.UserRepository;
import com.thales.JavaAPI.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request ) {
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new RuntimeException("Senha incorreta");
    }

    String token = jwtService.generateToken(user.getEmail());

    return ResponseEntity.ok(new AuthResponseDTO(token));
  }
  
}
