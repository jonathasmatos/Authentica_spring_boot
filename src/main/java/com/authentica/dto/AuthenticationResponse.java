package com.authentica.dto;

/**
 * DTO de Resposta do Servidor.
 * Quando o usuário loga ou registra com sucesso, nós devolvemos o Token JWT
 * para ele.
 */
public record AuthenticationResponse(
        String token) {
}
