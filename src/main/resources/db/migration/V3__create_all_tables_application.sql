
-- Criando o schema se não existir
CREATE SCHEMA IF NOT EXISTS fixyou;

CREATE TABLE fixyou.tb_sample (
	id int8 NOT NULL,
	name varchar(255) NULL,
	CONSTRAINT tb_sample_pkey PRIMARY KEY (id)
);