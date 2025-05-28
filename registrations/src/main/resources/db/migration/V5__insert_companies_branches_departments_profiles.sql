-- Companies
INSERT INTO fixyou.tb_company (name, cnpj, description) VALUES
('Company A', '12.345.678/0001-90', 'Main company for POC');

-- Branches
INSERT INTO fixyou.tb_branch (name, code, address, company_id) VALUES
('Branch SP', 'SP001', 'Av. Paulista, São Paulo', 1),
('Branch RJ', 'RJ001', 'Av. Atlântica, Rio de Janeiro', 1);

-- Departments
INSERT INTO fixyou.tb_department (name, branch_id) VALUES
('Finance', 1),
('IT', 1),
('HR', 2);

-- Profiles
INSERT INTO fixyou.tb_profile (preferred_shift, job_title, branch_id) VALUES
('Morning', 'Manager', 1),
('Evening', 'Developer', 1),
('Night', 'Analyst', 2);

-- Profile-Department relationships
INSERT INTO fixyou.tb_profile_departments (profile_id, department_id) VALUES
(1, 1), -- Profile 1 -> Finance
(2, 2), -- Profile 2 -> IT
(3, 3); -- Profile 3 -> HR

-- Person Data
INSERT INTO fixyou.tb_person_data (contract_type, cpf, cnpj, rg, cnh, profile_id) VALUES
('CLT', '123.456.789-00', null, 'MG-12.345.678', '12345678900', 1),
('PJ', null, '12.345.678/0001-90', null, null, 2),
('CLT', '987.654.321-00', null, 'SP-98.765.432', '98765432100', 3);
