package com.expense.tracker.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private Long userId;
    private String name;
    private String email;
}