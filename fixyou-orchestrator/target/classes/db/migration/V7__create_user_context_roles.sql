-- V7__create_user_context_roles.sql
CREATE TABLE IF NOT EXISTS fixyou.tb_user_context_roles (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    role VARCHAR(100) NOT NULL,
    institution_id INT,
    unit_id INT,
    department_id INT,
    sector_id INT,
    FOREIGN KEY (user_id) REFERENCES fixyou.tb_users(id) ON DELETE CASCADE,
    FOREIGN KEY (institution_id) REFERENCES fixyou.tb_institution(id) ON DELETE SET NULL,
    FOREIGN KEY (unit_id) REFERENCES fixyou.tb_unit(id) ON DELETE SET NULL,
    FOREIGN KEY (department_id) REFERENCES fixyou.tb_department(id) ON DELETE SET NULL,
    FOREIGN KEY (sector_id) REFERENCES fixyou.tb_sector(id) ON DELETE SET NULL
);
