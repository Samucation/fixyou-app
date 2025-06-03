-- V2__create_tables_institution_unit_department_sector.sql
CREATE TABLE IF NOT EXISTS fixyou.tb_institution (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_by INT
);

CREATE TABLE IF NOT EXISTS fixyou.tb_unit (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(100),
    address VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),
    institution_id INT NOT NULL,
    FOREIGN KEY (institution_id) REFERENCES fixyou.tb_institution(id)
);

CREATE TABLE IF NOT EXISTS fixyou.tb_department (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    unit_id INT NOT NULL,
    department_type_id INT,
    FOREIGN KEY (unit_id) REFERENCES fixyou.tb_unit(id)
);

CREATE TABLE IF NOT EXISTS fixyou.tb_sector (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    department_id INT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES fixyou.tb_department(id)
);