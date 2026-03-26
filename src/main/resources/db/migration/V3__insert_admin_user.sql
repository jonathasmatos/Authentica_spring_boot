INSERT INTO users (id, email, password, role)
VALUES (
    '550e8400-e29b-41d4-a716-446655440000', 
    'admin@authentica.com', 
    '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOnu', 
    'ADMIN'
) ON CONFLICT (email) DO NOTHING;
