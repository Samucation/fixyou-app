-- V4__insert_roles.sql
INSERT INTO fixyou.tb_roles (name) VALUES
('ADMIN'),
('GESTOR'),
('MEDICO'),
('PROFISSIONAL');

-- V5__insert_institutions_units_departments_sectors_profiles.sql
INSERT INTO fixyou.tb_institution (name, cnpj, description) VALUES
('Institution A', '12.345.678/0001-90', 'Main institution for POC');

INSERT INTO fixyou.tb_unit (name, code, address, city, state, zip_code, institution_id) VALUES
('Unit SP', 'SP001', 'Av. Paulista', 'São Paulo', 'SP', '01311-000', 1),
('Unit RJ', 'RJ001', 'Av. Atlântica', 'Rio de Janeiro', 'RJ', '22010-000', 1);

INSERT INTO fixyou.tb_department (name, unit_id) VALUES
('Finance', 1),
('IT', 1),
('HR', 2);

INSERT INTO fixyou.tb_sector (name, department_id) VALUES
('Payments', 1),
('Infrastructure', 2),
('Recruitment', 3);

INSERT INTO fixyou.tb_profile (preferred_shift, job_title, unit_id) VALUES
('Morning', 'Manager', 1),
('Evening', 'Developer', 1),
('Night', 'Analyst', 2);

INSERT INTO fixyou.tb_profile_departments (profile_id, department_id) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO fixyou.tb_profile_sectors (profile_id, sector_id) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO fixyou.tb_person_data (contract_type, cpf, cnpj, rg, cnh, profile_id) VALUES
('CLT', '123.456.789-00', null, 'MG-12.345.678', '12345678900', 1),
('PJ', null, '12.345.678/0001-90', null, null, 2),
('CLT', '987.654.321-00', null, 'SP-98.765.432', '98765432100', 3);
