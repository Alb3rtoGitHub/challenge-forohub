-- Crear tabla cursos
CREATE TABLE cursos (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nombre_curso VARCHAR(255) NOT NULL,-- Almacena el valor del Enum
                        categoria VARCHAR(255) NOT NULL -- Almacena el valor del Enum
);

-- Crear tabla topicos
CREATE TABLE topicos (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         titulo VARCHAR(255) NOT NULL,
                         mensaje TEXT NOT NULL,
                         fecha_de_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         status VARCHAR(50), -- Almacena el valor del Enum como String
                         autor VARCHAR(255) NOT NULL,
                         curso_id BIGINT,
                         FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);