CREATE TABLE fixyou.tb_company (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE fixyou.tb_branch (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(100),
    address VARCHAR(255),
    company_id INT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES fixyou.tb_company(id)
);

CREATE TABLE fixyou.tb_department (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    branch_id INT NOT NULL,
    FOREIGN KEY (branch_id) REFERENCES fixyou.tb_branch(id)
);
