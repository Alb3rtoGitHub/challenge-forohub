-- Crear tabla perfiles
CREATE TABLE perfiles
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL-- Almacena el valor del Enum
);

-- Crear tabla usuarios
CREATE TABLE usuarios
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre             VARCHAR(255) NOT NULL,
    correo_electronico VARCHAR(255) NOT NULL UNIQUE,
    contrasena         VARCHAR(255)
);

-- Crear tabla usuarios-perfiles
CREATE TABLE usuarios_perfiles
(
    usuarios_id BIGINT NOT NULL,
    perfiles_id  BIGINT NOT NULL,
    PRIMARY KEY (usuarios_id, perfiles_id),
    FOREIGN KEY (usuarios_id) REFERENCES usuarios (id),
    FOREIGN KEY (perfiles_id) REFERENCES perfiles (id)
);

-- Insertar datos iniciales en la tabla perfiles
INSERT INTO perfiles (nombre)
VALUES ('ROLE_ALUMNO'),
       ('ROLE_PROFESOR'),
       ('ROLE_ADMIN');