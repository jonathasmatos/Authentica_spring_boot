-- V5: Adicionar coluna 'active' para controle de status do usuário
ALTER TABLE users ADD COLUMN active BOOLEAN DEFAULT TRUE;

-- Garantir que o admin inicial esteja ativo
UPDATE users SET active = TRUE WHERE email = 'admin@authentica.com';
