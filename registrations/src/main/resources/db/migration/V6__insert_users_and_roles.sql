-- Users
INSERT INTO fixyou.tb_users (username, keycloak_id, profile_id) VALUES
('admin', 'uuid-admin-123', 1),
('gestor', 'uuid-gestor-456', 2),
('medico', 'uuid-medico-789', 3);

-- User-Role relationships
INSERT INTO fixyou.tb_user_roles (user_id, role_id) VALUES
(1, 1), -- admin -> ADMIN
(2, 2), -- gestor -> GESTOR
(3, 3); -- medico -> MEDICO
