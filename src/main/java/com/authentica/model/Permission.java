package com.authentica.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Entidade para Permissões Granulares.
 * Professor explica: Aqui definiremos o que cada "subsistema" exige (ex:
 * 'RH_ACCESS').
 */
@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    public Permission(String name) {
        this.name = name;
    }
}
