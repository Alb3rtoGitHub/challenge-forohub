package com.aluracursos.challenge_forohub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuesta(
        @NotBlank
        String mensaje,

        @NotNull
        Long topicoId,

        @NotNull
        Long usuarioId
) {
}