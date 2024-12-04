package com.aluracursos.challenge_forohub.domain.curso;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CursoRepository extends CrudRepository<Curso, Long> {
    Optional<Curso> findByNombreCursoAndCategoria(NombreCurso nombreCurso, Categoria categoria);
}
