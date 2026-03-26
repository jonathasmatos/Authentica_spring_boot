package com.authentica.repository;

import com.authentica.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório de Usuário (Moderno 2026).
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Professor explica: Usar Optional é a melhor prática para evitar
    // NullPointerException.
    Optional<User> findByEmail(String email);
}
