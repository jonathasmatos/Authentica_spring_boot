package com.authentica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Chave de ignição do projeto Authentica.
 * Professor explica: Sem essa classe com @SpringBootApplication, o projeto não
 * sabe por onde começar!
 */
@SpringBootApplication
public class AuthenticaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticaApplication.class, args);
    }
}
