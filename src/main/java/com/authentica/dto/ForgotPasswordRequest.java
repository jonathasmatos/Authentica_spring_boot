package com.authentica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Record para o pedido de "Esqueci minha senha".
 */
public record ForgotPasswordRequest(
        @NotBlank(message = "O email é obrigatório") @Email(message = "Formato de email inválido") String email) {
}
