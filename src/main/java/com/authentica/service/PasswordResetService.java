package com.authentica.service;

import com.authentica.dto.ForgotPasswordRequest;
import com.authentica.dto.ResetPasswordRequest;
import com.authentica.model.PasswordResetToken;
import com.authentica.model.User;
import com.authentica.repository.PasswordResetTokenRepository;
import com.authentica.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public void initiateReset(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        tokenRepository.deleteByUser(user);
        String token = UUID.randomUUID().toString();
        tokenRepository.save(new PasswordResetToken(token, user, 15));
        emailService.sendPasswordResetEmail(user.getEmail(), token);
    }

    @Transactional
    public void reset(ResetPasswordRequest request) {
        var resetToken = tokenRepository.findByToken(request.token())
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (resetToken.isExpired())
            throw new RuntimeException("Token expirado");

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
    }
}
