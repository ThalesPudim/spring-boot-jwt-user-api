  package com.thales.JavaAPI.service;

  import com.thales.JavaAPI.dto.UserRequestDTO;
  import com.thales.JavaAPI.dto.UserResponseDTO;
  import com.thales.JavaAPI.entity.User;
  import com.thales.JavaAPI.exception.EmailAlreadyExistsException;
  import com.thales.JavaAPI.repository.UserRepository;
  import org.springframework.stereotype.Service;
  import org.springframework.security.core.userdetails.UserDetails;
  import org.springframework.security.core.userdetails.UserDetailsService;
  import org.springframework.security.core.userdetails.UsernameNotFoundException;
  import org.springframework.security.crypto.password.PasswordEncoder;
  import com.thales.JavaAPI.security.Role;

  import java.time.LocalDateTime;
  import java.util.List;
  import java.util.stream.Collectors;

  @Service
  public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<UserResponseDTO> findAllUsers() {
      return userRepository.findAll()
            .stream()
            .map(user -> UserResponseDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .createdAt(user.getCreatedAt())
                    .build())
            .collect(Collectors.toList());
    }

    public UserResponseDTO findUserById(Long id) {

      User user = userRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

      return UserResponseDTO.builder()
              .id(user.getId())
              .name(user.getName())
              .email(user.getEmail())
              .createdAt(user.getCreatedAt())
              .build();
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {

      User user = userRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

      user.setName(dto.getName());
      user.setEmail(dto.getEmail());
      user.setPassword(dto.getPassword());

      User updatedUser = userRepository.save(user);

      return UserResponseDTO.builder()
              .id(updatedUser.getId())
              .name(updatedUser.getName())
              .email(updatedUser.getEmail())
              .createdAt(updatedUser.getCreatedAt())
              .build();
    } 

    public void deleteUser(Long id) {

      User user = userRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

      userRepository.delete(user);
    }

    public UserResponseDTO createUser(UserRequestDTO dto) {

      if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
        throw new EmailAlreadyExistsException("Email já cadastrado");
      }

      User user = User.builder()
          .name(dto.getName())
          .email(dto.getEmail())
          .password(passwordEncoder.encode(dto.getPassword()))
          .role(Role.ROLE_USER)
          .createdAt(LocalDateTime.now())
          .build();
      
      User savedUser = userRepository.save(user);

      return UserResponseDTO.builder()
          .id(savedUser.getId())
          .name(savedUser.getName())
          .email(savedUser.getEmail())
          .createdAt(savedUser.getCreatedAt())
          .build();
    }

  }
