package com.aluracursos.challenge_forohub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Optional<Respuesta> findByMensajeContains(@NotBlank String mensaje);

    Optional<Respuesta> findByMensaje(@NotBlank String mensaje);

    @Query("""
                    SELECT r
                    FROM Respuesta r
                    WHERE (:mensaje IS NULL OR LOWER(r.mensaje) LIKE LOWER(CONCAT('%', :mensaje, '%')))
                    AND (:autorId IS NULL OR r.autor.id = :autorId)
                    AND (:topicoId IS NULL OR r.topico.id = :topicoId)
            
            """)
    Page<Respuesta> buscarRespuestaConFiltros(@Param("mensaje") String mensaje, @Param("topicoId") Long topicoId, @Param("autorId") Long autorId, Pageable pageable);
}
