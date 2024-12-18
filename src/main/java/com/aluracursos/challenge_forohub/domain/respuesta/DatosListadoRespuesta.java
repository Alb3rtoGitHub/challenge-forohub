package com.aluracursos.challenge_forohub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosListadoRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        String nombretopico,
        String nombreusuarioRespuesta,
        Boolean solucion
) {
    public DatosListadoRespuesta (Respuesta respuesta){
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getTopico().getTitulo(),
                respuesta.getAutor().getNombre(),
                respuesta.getSolucion()
        );
    }
}
