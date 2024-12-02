package com.aluracursos.challenge_forohub.domain.topico;

public record DatosRegistroTopico(
        String titulo,
        String mensaje,
        String autor,
        Curso curso
) {
}
