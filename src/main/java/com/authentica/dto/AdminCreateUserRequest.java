package com.authentica.dto;

public record AdminCreateUserRequest(
        String email,
        String password,
        boolean active) {
}
