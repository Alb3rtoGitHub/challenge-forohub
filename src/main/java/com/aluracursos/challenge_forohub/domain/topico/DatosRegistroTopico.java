package com.aluracursos.challenge_forohub.domain.topico;

import com.aluracursos.challenge_forohub.domain.curso.DatosCurso;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DatosRegistroTopico(
        String titulo,
        String mensaje,
        String autor,
        @JsonProperty("curso") DatosCurso datosCurso // Mapear "curso" a "datosCurso"
) {
}
