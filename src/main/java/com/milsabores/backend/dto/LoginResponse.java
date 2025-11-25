package com.milsabores.backend.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String role;
    private String name;

    public LoginResponse(String accessToken, Long id, String email, String role, String name) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.role = role;
        this.name = name;
    }
}