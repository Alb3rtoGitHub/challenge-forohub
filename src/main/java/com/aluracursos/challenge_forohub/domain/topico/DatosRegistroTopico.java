package com.aluracursos.challenge_forohub.domain.topico;

import com.aluracursos.challenge_forohub.domain.curso.Curso;

public record DatosRegistroTopico(
        String titulo,
        String mensaje,
        String autor,
        Curso curso
) {
}
