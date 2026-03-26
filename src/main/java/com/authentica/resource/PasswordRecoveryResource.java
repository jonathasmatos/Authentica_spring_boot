package com.authentica.resource;

import com.authentica.dto.ForgotPasswordRequest;
import com.authentica.dto.ResetPasswordRequest;
import com.authentica.service.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Recurso especializado em fluxos de recuperação de conta.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class PasswordRecoveryResource {

    private final PasswordResetService resetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        resetService.initiateReset(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        resetService.reset(request);
        return ResponseEntity.ok().build();
    }
}
