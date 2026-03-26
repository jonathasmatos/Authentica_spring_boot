-- V6: Consolidação de Acessos Administradores
-- Garante que o usuário professor@authentica.com seja ADMIN
-- Redefine a senha do admin@authentica.com para 'admin123'

-- 1. Criar/Garantir o usuário professor
INSERT INTO users (id, email, password, active)
VALUES ('550e8400-e29b-41d4-a716-446655440005', 'professor@authentica.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', TRUE)
ON CONFLICT (email) DO UPDATE SET active = TRUE, password = EXCLUDED.password;

-- 2. Associar professor ao cargo de ADMIN
INSERT INTO user_roles (user_id, role_id)
SELECT id, '550e8400-e29b-41d4-a716-446655440001' FROM users WHERE email = 'professor@authentica.com'
ON CONFLICT DO NOTHING;

-- 3. Resetar senha do admin original para 'admin123'
UPDATE users 
SET password = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', active = TRUE
WHERE email = 'admin@authentica.com';
