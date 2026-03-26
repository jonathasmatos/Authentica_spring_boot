-- V9: Corrige a senha dos administradores para 'admin123'
-- A V8 utilizou o hash padrão de 'password' invés de 'admin123' na BCrypt.
-- Hash de 'admin123' é $2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOnu

UPDATE users 
SET password = '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOnu'
WHERE email IN ('professor@authentica.com', 'admin@authentica.com');
