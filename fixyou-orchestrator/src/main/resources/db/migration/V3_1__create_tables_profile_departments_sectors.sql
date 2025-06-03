-- V3_1__create_tables_profile_departments_sectors.sql
CREATE TABLE IF NOT EXISTS fixyou.tb_profile_departments (
    profile_id INT NOT NULL,
    department_id INT NOT NULL,
    PRIMARY KEY (profile_id, department_id),
    FOREIGN KEY (profile_id) REFERENCES fixyou.tb_profile(id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES fixyou.tb_department(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS fixyou.tb_profile_sectors (
    profile_id INT NOT NULL,
    sector_id INT NOT NULL,
    PRIMARY KEY (profile_id, sector_id),
    FOREIGN KEY (profile_id) REFERENCES fixyou.tb_profile(id) ON DELETE CASCADE,
    FOREIGN KEY (sector_id) REFERENCES fixyou.tb_sector(id) ON DELETE CASCADE
);