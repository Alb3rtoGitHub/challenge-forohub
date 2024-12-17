package com.aluracursos.challenge_forohub.domain.usuario;

import com.aluracursos.challenge_forohub.domain.perfil.Perfil;
import com.aluracursos.challenge_forohub.domain.perfil.Rol;

import java.util.List;

public record DatosListadoUsuario(
        Long id,
        String nombre,
        String correoElectronico,
        List<Rol> perfiles
) {
    public DatosListadoUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico(),
                usuario.getPerfiles().stream().map(Perfil::getNombre).toList()
        );
    }
}
