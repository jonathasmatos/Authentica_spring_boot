package com.authentica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Record para a redefinição final da senha.
 */
public record ResetPasswordRequest(
        @NotBlank(message = "O token é obrigatório") String token,

        @NotBlank(message = "A nova senha é obrigatória") @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres") String newPassword) {
}
