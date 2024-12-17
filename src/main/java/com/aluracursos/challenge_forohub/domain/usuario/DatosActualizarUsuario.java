package com.aluracursos.challenge_forohub.domain.usuario;

import com.aluracursos.challenge_forohub.domain.perfil.Rol;
import jakarta.validation.constraints.*;

import java.util.List;

public record DatosActualizarUsuario(
//        @NotNull(message = "El id no puede ser nulo")
        Long id,

        @Email(message = "El correo electrónico debe tener un formato válido")
        String correoElectronico,

        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String contrasena,

        List<Rol> perfiles
) {
}
