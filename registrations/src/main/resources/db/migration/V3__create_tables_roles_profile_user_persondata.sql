CREATE TABLE fixyou.tb_roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE fixyou.tb_profile (
    id SERIAL PRIMARY KEY,
    preferred_shift VARCHAR(50),
    job_title VARCHAR(100),
    branch_id INT NOT NULL,
    FOREIGN KEY (branch_id) REFERENCES fixyou.tb_branch(id)
);

CREATE TABLE fixyou.tb_profile_departments (
    profile_id INT NOT NULL,
    department_id INT NOT NULL,
    PRIMARY KEY (profile_id, department_id),
    FOREIGN KEY (profile_id) REFERENCES fixyou.tb_profile(id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES fixyou.tb_department(id) ON DELETE CASCADE
);

CREATE TABLE fixyou.tb_person_data (
    id SERIAL PRIMARY KEY,
    contract_type VARCHAR(50) NOT NULL,
    cpf VARCHAR(14),
    cnpj VARCHAR(18),
    rg VARCHAR(20),
    cnh VARCHAR(20),
    profile_id INT NOT NULL UNIQUE,
    FOREIGN KEY (profile_id) REFERENCES fixyou.tb_profile(id) ON DELETE CASCADE
);

CREATE TABLE fixyou.tb_users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    keycloak_id VARCHAR(255) NOT NULL UNIQUE,
    profile_id INT NOT NULL,
    FOREIGN KEY (profile_id) REFERENCES fixyou.tb_profile(id) ON DELETE CASCADE
);

CREATE TABLE fixyou.tb_user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES fixyou.tb_users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES fixyou.tb_roles(id) ON DELETE CASCADE
);
