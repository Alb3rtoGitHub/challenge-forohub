package com.aluracursos.challenge_forohub.domain.topico;

import com.aluracursos.challenge_forohub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        StatusTopico statusTopico,
        String autor,
        String curso
) {
    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                String.valueOf(topico.getCurso().getNombreCurso())
        );
    }
}
