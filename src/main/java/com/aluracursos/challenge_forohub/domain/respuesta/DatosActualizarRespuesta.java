package com.aluracursos.challenge_forohub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizarRespuesta(
        @NotBlank(message = "El mensaje no puede estar vacío.")
        String mensaje
) {
}
