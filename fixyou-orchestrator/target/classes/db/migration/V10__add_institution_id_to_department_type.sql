-- Adiciona o campo institution_id (pode ser NULL para tipos globais)
ALTER TABLE tb_department_type ADD COLUMN institution_id INT;

-- Adiciona a foreign key com a tabela de instituições
ALTER TABLE tb_department_type
ADD CONSTRAINT fk_department_type_institution
FOREIGN KEY (institution_id) REFERENCES tb_institution(id);

-- (Opcional) Atualiza tipos existentes como globais (NULL por padrão)
-- Nenhuma alteração adicional necessária se for apenas para permitir os dois casos
