package com.authentica.service;

import com.authentica.dto.AuthenticationResponse;
import com.authentica.dto.RegisterRequest;
import com.authentica.model.User;
import com.authentica.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Especialista no registro de novas contas (Onboarding).
 */
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        var user = new User(
                request.email(),
                passwordEncoder.encode(request.password()));

        userRepository.save(user);
        return new AuthenticationResponse(jwtService.generateToken(user));
    }
}
