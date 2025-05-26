INSERT INTO fixyou.tb_users (username, password, profile_id) VALUES
('admin', '$2a$10$encryptedpasswordadmin', 1),
('gestor', '$2a$10$encryptedpasswordgestor', 2),
('medico', '$2a$10$encryptedpasswordmedico', 3);

-- Relacionar usuários às roles
INSERT INTO fixyou.tb_user_roles (user_id, role_id) VALUES
(1, 1), -- admin -> ADMIN
(2, 2), -- gestor -> GESTOR
(3, 3); -- medico -> MEDICO
