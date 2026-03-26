-- V4: Reestruturação para RBAC Dinâmico e Modular
-- Professor explica: Deletamos a coluna 'role' estática e criamos as tabelas de junção.

-- 1. Criação das novas tabelas de Acesso
CREATE TABLE permissions (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE roles (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE role_permissions (
    role_id UUID NOT NULL,
    permission_id UUID NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT fk_permission FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

CREATE TABLE user_roles (
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_user FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- 2. Migração do Administrador para a nova estrutura
-- Primeiro adicionamos a Role ADMIN
INSERT INTO roles (id, name) VALUES ('550e8400-e29b-41d4-a716-446655440001', 'ADMIN');

-- Relacionamos o usuário administrador com a Role ADMIN
INSERT INTO user_roles (user_id, role_id) 
SELECT id, '550e8400-e29b-41d4-a716-446655440001' FROM users WHERE email = 'admin@authentica.com';

-- 3. Limpeza
ALTER TABLE users DROP COLUMN role;
