-- V6__insert_users_and_roles.sql

-- Inserção de usuários vinculados a perfis já existentes
INSERT INTO fixyou.tb_users (username, keycloak_id, profile_id) VALUES
('admin', 'uuid-admin-123', 1),
('gestor', 'uuid-gestor-456', 2),
('medico', 'uuid-medico-789', 3);

-- Relacionamento com papéis, só se existirem
DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM fixyou.tb_roles WHERE name = 'ADMIN') THEN
    INSERT INTO fixyou.tb_user_roles (user_id, role_id)
    VALUES (1, (SELECT id FROM fixyou.tb_roles WHERE name = 'ADMIN'));
  END IF;

  IF EXISTS (SELECT 1 FROM fixyou.tb_roles WHERE name = 'GESTOR') THEN
    INSERT INTO fixyou.tb_user_roles (user_id, role_id)
    VALUES (2, (SELECT id FROM fixyou.tb_roles WHERE name = 'GESTOR'));
  END IF;

  IF EXISTS (SELECT 1 FROM fixyou.tb_roles WHERE name = 'MEDICO') THEN
    INSERT INTO fixyou.tb_user_roles (user_id, role_id)
    VALUES (3, (SELECT id FROM fixyou.tb_roles WHERE name = 'MEDICO'));
  END IF;
END $$;
