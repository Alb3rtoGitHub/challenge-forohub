-- Agregar la columna usuario_id a la tabla topicos
ALTER TABLE topicos
    ADD COLUMN usuario_id BIGINT;

-- Eliminar la columna autor (tipo String)
ALTER TABLE topicos
DROP
COLUMN autor;

-- Relacion entre topicos y usuarios
ALTER TABLE topicos
ADD CONSTRAINT fk_usuario
FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE;



