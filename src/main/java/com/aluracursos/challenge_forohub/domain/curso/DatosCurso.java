package com.aluracursos.challenge_forohub.domain.curso;

import jakarta.validation.constraints.NotNull;

public record DatosCurso(

        @NotNull
        NombreCurso nombreCurso,

        @NotNull
        Categoria categoria
) {
}
