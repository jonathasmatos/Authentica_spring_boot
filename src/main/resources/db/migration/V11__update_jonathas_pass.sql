-- V11: Atualiza senha de admin@admin.com para 'admin123'
-- Garante o hash correto mesmo após limpar/recriar o banco

INSERT INTO users (id, email, password, active)
VALUES ('550e8400-e29b-41d4-a716-446655440006', 'admin@admin.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOnu', TRUE)
ON CONFLICT (email) DO UPDATE SET active = TRUE, password = EXCLUDED.password;
