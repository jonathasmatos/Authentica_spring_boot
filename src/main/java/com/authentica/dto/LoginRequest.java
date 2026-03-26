package com.authentica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para o processo de Login.
 */
public record LoginRequest(

        @NotBlank(message = "O email é obrigatório") @Email(message = "Formato de email inválido") String email,

        @NotBlank(message = "A senha é obrigatória") String password) {
}
