package com.khan.quiz.controller;

import com.khan.quiz.dto.ApiResponse;
import com.khan.quiz.dto.AuthResponse;
import com.khan.quiz.dto.LoginRequest;
import com.khan.quiz.dto.RegisterRequest;
import com.khan.quiz.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register/admin")
    public ResponseEntity<ApiResponse<AuthResponse>> registerAdmin(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = authenticationService.registerAdmin(registerRequest);

        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Registration successful",
                authResponse,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<ApiResponse<AuthResponse>> registerTeacher(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = authenticationService.registerTeacher(registerRequest);

        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Registration successful",
                authResponse,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/register/student")
    public ResponseEntity<ApiResponse<AuthResponse>> registerStudent(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = authenticationService.registerStudent(registerRequest);

        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Registration successful",
                authResponse,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authenticationService.login(request);

        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Login successful",
                authResponse,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(apiResponse);
    }
}
