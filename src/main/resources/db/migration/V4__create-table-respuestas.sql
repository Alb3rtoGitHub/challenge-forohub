-- Crear tabla respuestas
CREATE TABLE respuestas (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            mensaje TEXT NOT NULL,
                            fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            topico_id BIGINT NOT NULL,
                            usuario_id BIGINT NOT NULL,
                            solucion BOOLEAN DEFAULT FALSE,
                            FOREIGN KEY (topico_id) REFERENCES topicos (id) ON DELETE CASCADE,
                            FOREIGN KEY (usuario_id) REFERENCES usuarios (id) ON DELETE CASCADE
);
