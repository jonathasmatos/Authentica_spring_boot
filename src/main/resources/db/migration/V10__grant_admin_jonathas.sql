-- V10: Limpeza de acessos e concessao de admin
-- Remove os antigos usuários que não serão acessados
DELETE FROM user_roles WHERE user_id IN (
    SELECT id FROM users WHERE email NOT IN ('admin@admin.com')
);

DELETE FROM users WHERE email NOT IN ('admin@admin.com');

-- Garante que o usuario exista com senha admin123
INSERT INTO users (id, email, password, active)
VALUES ('550e8400-e29b-41d4-a716-446655440006', 'admin@admin.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOnu', TRUE)
ON CONFLICT (email) DO UPDATE SET active = TRUE, password = EXCLUDED.password;

-- Concede cargo de ADMIN (id '550e8400-e29b-41d4-a716-446655440001' da role)
INSERT INTO user_roles (user_id, role_id)
SELECT id, '550e8400-e29b-41d4-a716-446655440001' FROM users WHERE email = 'admin@admin.com'
ON CONFLICT DO NOTHING;
