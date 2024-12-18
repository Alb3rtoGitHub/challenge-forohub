package com.aluracursos.challenge_forohub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Optional<Object> findByMensajeContains(@NotBlank String mensaje);
}
