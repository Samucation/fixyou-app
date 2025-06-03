-- Instituição principal
INSERT INTO fixyou.tb_institution (name, cnpj, description) VALUES
('Hospital Municipal São Lucas', '12.345.678/0001-90', 'Hospital geral de atendimento público');

-- Unidades hospitalares
INSERT INTO fixyou.tb_unit (name, code, address, city, state, zip_code, institution_id) VALUES
('Unidade Central', 'UC001', 'Av. Paulista', 'São Paulo', 'SP', '01311-000', 1),
('Unidade Infantil', 'UI001', 'Rua das Crianças', 'São Paulo', 'SP', '01311-001', 1);

-- Departamentos (com tipo associado)
INSERT INTO fixyou.tb_department (name, unit_id, department_type_id) VALUES
('Clínica Geral', 1, 1),        -- tipo 1 = Clínica Geral
('Pediatria', 2, 2),            -- tipo 2 = Pediatria
('Enfermagem', 1, 3);           -- tipo 3 = Enfermagem

-- Setores
INSERT INTO fixyou.tb_sector (name, department_id) VALUES
('Consultas Ambulatoriais', 1),
('Vacinação Infantil', 2),
('Triagem de Pacientes', 3);

-- Perfis (colaboradores)
INSERT INTO fixyou.tb_profile (preferred_shift, job_title, unit_id) VALUES
('Manhã', 'Gestor Clínico', 1),
('Tarde', 'Médico Pediatra', 2),
('Noite', 'Enfermeira', 1);

-- Vínculos com departamentos
INSERT INTO fixyou.tb_profile_departments (profile_id, department_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Vínculos com setores
INSERT INTO fixyou.tb_profile_sectors (profile_id, sector_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Dados pessoais
INSERT INTO fixyou.tb_person_data (contract_type, cpf, cnpj, rg, cnh, profile_id) VALUES
('CLT', '123.456.789-00', null, 'MG-12.345.678', '12345678900', 1),
('PJ', null, '12.345.678/0001-90', null, null, 2),
('CLT', '987.654.321-00', null, 'SP-98.765.432', '98765432100', 3);
