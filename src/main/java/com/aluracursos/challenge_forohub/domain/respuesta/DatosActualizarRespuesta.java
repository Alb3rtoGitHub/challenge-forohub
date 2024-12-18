package com.aluracursos.challenge_forohub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizarRespuesta(
        @NotBlank String mensaje,
        Boolean solucion
) {
}
