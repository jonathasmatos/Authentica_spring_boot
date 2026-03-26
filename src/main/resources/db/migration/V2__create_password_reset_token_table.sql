-- V2: Criação da Tabela de Tokens de Recuperação de Senha
CREATE TABLE password_reset_token (
    id UUID PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id UUID NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_user_reset FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
