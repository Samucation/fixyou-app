-- V5__insert_institutions_units_departments_sectors_profiles.sql

-- Inserção segura de nova instituição
INSERT INTO fixyou.tb_institution (name, cnpj, description)
SELECT 'Institution A', '00.000.000/0001-00', 'Instituição fictícia de exemplo'
WHERE NOT EXISTS (
  SELECT 1 FROM fixyou.tb_institution WHERE cnpj = '00.000.000/0001-00'
);

-- Unidades (sem ON CONFLICT)
INSERT INTO fixyou.tb_unit (name, code, address, city, state, zip_code, institution_id) VALUES
('Unit SP', 'SP001', 'Av. Paulista', 'São Paulo', 'SP', '01311-000', 2),
('Unit RJ', 'RJ001', 'Av. Atlântica', 'Rio de Janeiro', 'RJ', '22010-000', 2);

-- Departamentos
INSERT INTO fixyou.tb_department (name, unit_id) VALUES
('Finance', 3),
('IT', 3),
('HR', 4);

-- Setores
INSERT INTO fixyou.tb_sector (name, department_id) VALUES
('Payments', 4),
('Infrastructure', 5),
('Recruitment', 6);

-- Perfis
INSERT INTO fixyou.tb_profile (preferred_shift, job_title, unit_id) VALUES
('Morning', 'Manager', 3),
('Evening', 'Developer', 3),
('Night', 'Analyst', 4);

-- Relacionamento com departamentos
INSERT INTO fixyou.tb_profile_departments (profile_id, department_id) VALUES
(4, 4),
(5, 5),
(6, 6);

-- Relacionamento com setores
INSERT INTO fixyou.tb_profile_sectors (profile_id, sector_id) VALUES
(4, 4),
(5, 5),
(6, 6);

-- Dados pessoais
INSERT INTO fixyou.tb_person_data (contract_type, cpf, cnpj, rg, cnh, profile_id) VALUES
('CLT', '321.654.987-00', null, 'MG-98.765.432', '32165498700', 4),
('PJ', null, '00.000.000/0001-00', null, null, 5),
('CLT', '654.321.987-00', null, 'SP-87.654.321', '65432198700', 6);
