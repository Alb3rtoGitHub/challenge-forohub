package com.aluracursos.challenge_forohub.domain.topico;

import com.aluracursos.challenge_forohub.domain.curso.Curso;
import com.aluracursos.challenge_forohub.domain.curso.DatosCurso;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        StatusTopico statusTopico,
        String autor,
        Curso curso
) {
    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso()
        );
    }

}
