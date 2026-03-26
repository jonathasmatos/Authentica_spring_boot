-- Garante que os usuários de teste padrão possuam a ROLE_ADMIN
-- ROLE_ADMIN ID: 550e8400-e29b-41d4-a716-446655440001

INSERT INTO user_roles (user_id, role_id)
SELECT id, '550e8400-e29b-41d4-a716-446655440001'
FROM users
WHERE email IN ('admin@admin.com', 'teste@qa.com')
ON CONFLICT DO NOTHING;
