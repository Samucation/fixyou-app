-- V9__create_table_department_type_and_refactor_department.sql
CREATE TABLE IF NOT EXISTS fixyou.tb_department_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

INSERT INTO fixyou.tb_department_type (name) VALUES
('Clínica Geral'), ('Pediatria'), ('Enfermagem'),
('Cardiologia'), ('Ortopedia'), ('Neurologia'), ('Psiquiatria'), ('Dermatologia'),
('Ginecologia'), ('Obstetrícia'), ('Urologia'), ('Oncologia'), ('Endocrinologia'),
('Oftalmologia'), ('Otorrinolaringologia'), ('Reumatologia'), ('Nefrologia'),
('Gastroenterologia'), ('Infectologia'), ('Cirurgia Geral'), ('Urgência e Emergência'),
('Centro Cirúrgico'), ('UTI Adulto'), ('UTI Neonatal'), ('Anestesiologia'),
('Hematologia'), ('Hemodinâmica'), ('Laboratório de Análises Clínicas'), ('Banco de Sangue'),
('Radiologia'), ('Tomografia e Ressonância'), ('Fisioterapia'), ('Fonoaudiologia'),
('Nutrição Clínica'), ('Farmácia Hospitalar'), ('Psicologia Hospitalar'),
('Controle de Infecção Hospitalar'), ('Serviço Social'), ('Patologia Clínica'),
('Centro de Material e Esterilização (CME)'), ('Apoio Diagnóstico'), ('Ambulatório'),
('Internação'), ('Alta Complexidade'), ('Hospital Dia'),
('Atendimento Domiciliar (Home Care)'), ('Núcleo de Segurança do Paciente'),
('Educação Continuada'), ('Administração Hospitalar'), ('Financeiro'),
('Recursos Humanos'), ('Tecnologia da Informação'), ('Manutenção e Engenharia Clínica'),
('Hotelaria Hospitalar'), ('Recepção e Atendimento'), ('Ouvidoria');