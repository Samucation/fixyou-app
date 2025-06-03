ALTER TABLE tb_department_type ADD COLUMN institution_id INT;
ALTER TABLE tb_department_type
ADD CONSTRAINT fk_department_type_institution
FOREIGN KEY (institution_id) REFERENCES tb_institution(id);
