package com.aluracursos.challenge_forohub.domain.usuario;

import com.aluracursos.challenge_forohub.domain.perfil.Perfil;
import com.aluracursos.challenge_forohub.domain.perfil.Rol;
import com.aluracursos.challenge_forohub.domain.respuesta.DatosRespuestaRespuesta;
import com.aluracursos.challenge_forohub.domain.topico.DatosRespuestaTopico;

import java.util.List;

public record DatosRespuestaUsuario(
        Long id,
        String nombre,
        String correoElectronico,
        List<Rol> roles,
        List<DatosRespuestaRespuesta> respuestas
) {
    public DatosRespuestaUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico(),
                usuario.getPerfiles().stream().map(Perfil::getNombre).toList(),
                usuario.getRespuestas().stream().map(DatosRespuestaRespuesta::new).toList()
        );
    }
}
