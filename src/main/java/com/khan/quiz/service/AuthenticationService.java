package com.khan.quiz.service;

import com.khan.quiz.dto.AuthResponse;
import com.khan.quiz.dto.LoginRequest;
import com.khan.quiz.dto.RegisterRequest;
import com.khan.quiz.dto.UserDetailsDto;
import com.khan.quiz.exception.InvalidCredentialsException;
import com.khan.quiz.exception.ResourceNotFoundException;
import com.khan.quiz.exception.UserAlreadyExistsException;
import com.khan.quiz.model.Role;
import com.khan.quiz.model.User;
import com.khan.quiz.repository.UserRepository;
import com.khan.quiz.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse registerAdmin(RegisterRequest request) {
        return registerUser(request, Role.ADMIN);
    }

    @Transactional
    public AuthResponse registerTeacher(RegisterRequest request) {
        return registerUser(request, Role.TEACHER);
    }

    @Transactional
    public AuthResponse registerStudent(RegisterRequest request) {
        return registerUser(request, Role.STUDENT);
    }

    public AuthResponse registerUser(RegisterRequest request, Role role) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
        }

        // create user
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .createdAt(LocalDateTime.now())
                .build();

        // save user
        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser);

        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .role(savedUser.getRole())
                .build();

        return AuthResponse.builder()
                .token(token)
                .userDetailsDto(userDetailsDto)
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();

        return AuthResponse.builder()
                .token(token)
                .userDetailsDto(userDetailsDto)
                .build();
    }
}
