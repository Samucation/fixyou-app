-- Criação da tabela de tipos de departamento
CREATE TABLE tb_department_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Adiciona coluna para referenciar o tipo
ALTER TABLE tb_department ADD COLUMN department_type_id INT;

-- Adiciona FK
ALTER TABLE tb_department
ADD CONSTRAINT fk_department_type
FOREIGN KEY (department_type_id)
REFERENCES tb_department_type(id);

-- (Opcional) Atualiza os registros existentes com tipos default, se desejar popular
-- Exemplo:
INSERT INTO tb_department_type (name) VALUES ('Clínica Geral'), ('Pediatria'), ('Enfermagem');
UPDATE tb_department SET department_type_id = 1 WHERE name = 'Clínica Geral';
