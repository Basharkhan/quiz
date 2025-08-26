package com.khan.quiz.dto;

import com.khan.quiz.model.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailsDto {
    private String email;
    private String fullName;
    private Role role;
}
