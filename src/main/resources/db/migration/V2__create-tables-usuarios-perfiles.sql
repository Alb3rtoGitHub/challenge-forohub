-- Crear tabla perfiles
CREATE TABLE perfiles (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
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
CREATE TABLE usuarios_perfiles (
                                   usuario_id BIGINT NOT NULL,
                                   perfil_id BIGINT NOT NULL,
                                   PRIMARY KEY (usuario_id, perfil_id),
                                   FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
                                   FOREIGN KEY (perfil_id) REFERENCES perfiles(id)
);