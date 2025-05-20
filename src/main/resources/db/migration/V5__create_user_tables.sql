-- Criando o schema se não existir
CREATE SCHEMA IF NOT EXISTS amicred;

-- Criando a tabela de perfis
CREATE TABLE IF NOT EXISTS amicred.tb_profile (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL UNIQUE
);

-- Criando a tabela de usuários
CREATE TABLE IF NOT EXISTS amicred.tb_users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    profile_id INT NOT NULL,
    CONSTRAINT fk_user_profile FOREIGN KEY (profile_id) REFERENCES amicred.tb_profile(id)
);

-- Inserindo os perfis
INSERT INTO amicred.tb_profile (id, type) VALUES (1, 'EMPRESA') ON CONFLICT (id) DO NOTHING;
INSERT INTO amicred.tb_profile (id, type) VALUES (2, 'PESSOA_FISICA') ON CONFLICT (id) DO NOTHING;

-- Inserindo os usuários
INSERT INTO amicred.tb_users (id, username, password, profile_id) VALUES (1, 'joao.silva', 'senha123', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO amicred.tb_users (id, username, password, profile_id) VALUES (2, 'maria.oliveira', 'senha456', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO amicred.tb_users (id, username, password, profile_id) VALUES (3, 'pedro.santos', 'senha789', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO amicred.tb_users (id, username, password, profile_id) VALUES (4, 'ana.costa', 'senha321', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO amicred.tb_users (id, username, password, profile_id) VALUES (5, 'lucas.rocha', 'senha654', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO amicred.tb_users (id, username, password, profile_id) VALUES (6, 'juliana.mendes', 'senha987', 2) ON CONFLICT (id) DO NOTHING;
