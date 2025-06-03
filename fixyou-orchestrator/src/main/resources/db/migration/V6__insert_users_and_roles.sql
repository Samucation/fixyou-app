-- V6__insert_users_and_roles.sql
INSERT INTO fixyou.tb_users (username, keycloak_id, profile_id) VALUES
('admin', 'uuid-admin-123', 1),
('gestor', 'uuid-gestor-456', 2),
('medico', 'uuid-medico-789', 3);

INSERT INTO fixyou.tb_user_roles (user_id, role_id) VALUES
(1, 1),
(2, 2),
(3, 3);
