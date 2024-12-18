package com.aluracursos.challenge_forohub.domain.topico;

import com.aluracursos.challenge_forohub.domain.respuesta.DatosRespuestaRespuesta;

import java.time.LocalDateTime;
import java.util.List;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        StatusTopico statusTopico,
        String autor,
        String curso,
        List<DatosRespuestaRespuesta> respuestas
) {
    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                String.valueOf(topico.getCurso().getNombreCurso()),
                topico.getRespuestas().stream().map(DatosRespuestaRespuesta::new).toList()
        );
    }
}
