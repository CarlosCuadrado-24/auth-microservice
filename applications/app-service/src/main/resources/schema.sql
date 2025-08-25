-- Tabla: role
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT
);

-- Tabla: user_entity
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE,
    phone VARCHAR(30),
    email VARCHAR(100) UNIQUE,
    salary BIGINT,
    roles_id BIGINT,
    CONSTRAINT fk_user_roles FOREIGN KEY (roles_id) REFERENCES roles(id)
);
