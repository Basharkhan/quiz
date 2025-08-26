package com.khan.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String message;
    private LocalDateTime timestamp;
    private UserDetailsDto userDetailsDto;
}
