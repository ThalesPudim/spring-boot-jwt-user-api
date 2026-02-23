package com.thales.JavaAPI.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDTO {

  private Long id;
  private String name;
  private String email;
  private LocalDateTime createdAt;
}