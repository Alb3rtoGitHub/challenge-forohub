package com.aluracursos.challenge_forohub.domain.topico;

import com.aluracursos.challenge_forohub.domain.curso.NombreCurso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByActivoTrue(Pageable paginacion);

    @Query("SELECT t FROM Topico t JOIN t.curso c WHERE c.nombreCurso = :nombreCurso AND YEAR(t.fechaDeCreacion) = :anio")
    Page<Topico> buscarPorCursoYAnio(
            @Param("nombreCurso") NombreCurso nombreCurso,
            @Param("anio") Integer anio,
            Pageable paginacion
    );

    Topico getTopicoByIdAndActivoIsTrue(Long id);
}
