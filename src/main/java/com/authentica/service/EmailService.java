package com.authentica.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pelo envio de emails do sistema.
 * 
 * Professor explica: Usamos o JavaMailSender do Spring Boot Starter Mail.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@authentica.com");
        message.setTo(to);
        message.setSubject("Recuperação de Senha - Authentica");
        message.setText("Para recuperar sua senha, use o token abaixo no sistema:\n\n"
                + token + "\n\nEste token expira em 15 minutos.");

        mailSender.send(message);
    }
}
