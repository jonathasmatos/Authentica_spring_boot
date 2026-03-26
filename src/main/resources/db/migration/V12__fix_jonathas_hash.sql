-- V12: Garante a senha admin123 correspondente ao BCrypt hash valido.

UPDATE users 
SET password = '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOnu'
WHERE email = 'admin@admin.com';
