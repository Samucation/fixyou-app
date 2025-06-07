-- V1__initialschema.sql

CREATE SCHEMA IF NOT EXISTS fixyou;

-- =============================
-- Tabela: login
-- =============================
CREATE TABLE IF NOT EXISTS fixyou.login (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    keycloak_id VARCHAR(255) NOT NULL,
    first_access_at TIMESTAMP
);

-- =============================
-- Tabela: profile
-- =============================
CREATE TABLE IF NOT EXISTS fixyou.profile (
    id UUID PRIMARY KEY,
    person UUID NOT NULL,
    position VARCHAR(255),
    contract VARCHAR(255),
    start_date DATE,
    end_date DATE,
    term_lgpd BOOLEAN,
    has_admin BOOLEAN,
    has_mobile BOOLEAN,
    has_manager BOOLEAN,
    active BOOLEAN
);

-- =============================
-- Tabela: person
-- =============================
CREATE TABLE IF NOT EXISTS fixyou.person (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    cpf VARCHAR(20),
    cnpj VARCHAR(20),
    cnpj_name VARCHAR(255),
    email VARCHAR(255),
    birth_date DATE,
    phone VARCHAR(20),
    mobile_number VARCHAR(20),
    street VARCHAR(255),
    address_number VARCHAR(10),
    address_complement VARCHAR(255),
    neighborhood VARCHAR(255),
    state VARCHAR(100),
    city VARCHAR(100),
    country VARCHAR(100),
    cep VARCHAR(20),
    gender VARCHAR(10),
    photo_url TEXT
);

-- =============================
-- Tabela: profile_preferences
-- =============================
CREATE TABLE IF NOT EXISTS fixyou.profile_preferences (
    id UUID PRIMARY KEY,
    profile_id UUID NOT NULL,
    preference_id UUID NOT NULL,
    value TEXT
);
