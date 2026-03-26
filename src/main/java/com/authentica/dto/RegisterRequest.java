package com.authentica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para o Registro de um novo usuário.
 * 
 * Professor explica: Removi o campo Role daqui. Em um sistema de intranet,
 * o usuário se cadastra e o ADMIN libera os acessos aos módulos depois.
 */
public record RegisterRequest(
                @NotBlank(message = "O email é obrigatório") @Email(message = "Formato de email inválido") String email,

                @NotBlank(message = "A senha é obrigatória") @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres") String password) {
}
