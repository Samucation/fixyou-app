-- V2__insert_initial_logins_profiles.sql

-- Person: Administrador Gestor
INSERT INTO fixyou.person (
    id, name, cpf, email, birth_date,
    phone, mobile_number, street, address_number, neighborhood,
    state, city, country, cep, gender, photo_url
)
VALUES (
    '11111111-1111-1111-1111-111111111111',
    'Samuca Admin',
    '12345678900',
    'admin@fixyou.com',
    '1985-01-01',
    '1122223333',
    '11999999999',
    'Av. Principal',
    '123',
    'Centro',
    'SP',
    'São Paulo',
    'Brasil',
    '01000-000',
    'M',
    'https://cdn.fixyou.com/profiles/admin.png'
);

-- Person: Usuário Comum
INSERT INTO fixyou.person (
    id, name, cpf, email, birth_date,
    phone, mobile_number, street, address_number, neighborhood,
    state, city, country, cep, gender, photo_url
)
VALUES (
    '22222222-2222-2222-2222-222222222222',
    'Usuário Comum',
    '98765432100',
    'user@fixyou.com',
    '1990-06-07',
    '1133334444',
    '11988888888',
    'Rua Secundária',
    '456',
    'Bairro Legal',
    'RJ',
    'Rio de Janeiro',
    'Brasil',
    '22000-000',
    'F',
    'https://cdn.fixyou.com/profiles/user.png'
);

-- Login: Administrador Gestor
INSERT INTO fixyou.login (id, username, keycloak_id, first_access_at)
VALUES (
    '11111111-1111-1111-1111-111111111111',
    'admin@fixyou.com',
    'aaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee',
    current_timestamp
);

-- Login: Usuário Comum
INSERT INTO fixyou.login (id, username, keycloak_id, first_access_at)
VALUES (
    '22222222-2222-2222-2222-222222222222',
    'user@fixyou.com',
    'fffffff-gggg-hhhh-iiii-jjjjjjjjjjjj',
    current_timestamp
);

-- Profile: Administrador Gestor
INSERT INTO fixyou.profile (
    id, person, position, contract, start_date,
    term_lgpd, has_admin, has_mobile, has_manager, active
)
VALUES (
    '33333333-3333-3333-3333-333333333333',
    '11111111-1111-1111-1111-111111111111', -- match com login.id
    'Administrador Geral',
    'CLT',
    current_date,
    true,
    true,
    true,
    true,
    true
);

-- Profile: Usuário Comum
INSERT INTO fixyou.profile (
    id, person, position, contract, start_date,
    term_lgpd, has_admin, has_mobile, has_manager, active
)
VALUES (
    '44444444-4444-4444-4444-444444444444',
    '22222222-2222-2222-2222-222222222222', -- match com login.id
    'Analista',
    'PJ',
    current_date,
    true,
    false,
    false,
    false,
    true
);

-- Profile Preferences: Administrador Gestor
INSERT INTO fixyou.profile_preferences (id, profile_id, preference_id, value)
VALUES
    ('55555555-5555-5555-5555-555555555555', '33333333-3333-3333-3333-333333333333', 'aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'DARK_MODE'),
    ('55555555-5555-5555-5555-555555555556', '33333333-3333-3333-3333-333333333333', 'aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaab', 'PT-BR');

-- Profile Preferences: Usuário Comum
INSERT INTO fixyou.profile_preferences (id, profile_id, preference_id, value)
VALUES
    ('66666666-6666-6666-6666-666666666666', '44444444-4444-4444-4444-444444444444', 'bbbb2222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'LIGHT_MODE'),
    ('66666666-6666-6666-6666-666666666667', '44444444-4444-4444-4444-444444444444', 'bbbb2222-bbbb-bbbb-bbbb-bbbbbbbbbbbc', 'EN-US');
