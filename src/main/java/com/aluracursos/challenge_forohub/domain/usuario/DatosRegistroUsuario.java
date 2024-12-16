package com.aluracursos.challenge_forohub.domain.usuario;

import com.aluracursos.challenge_forohub.domain.perfil.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record DatosRegistroUsuario(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,

        @NotBlank(message = "El correo electrónico no puede estar vacío")
        @Email(message = "Debe proporcionar un correo electrónico válido")
        String correoElectronico,

        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String contrasena,

        @NotNull(message = "Debe proporcionar al menos un perfil")
        List<Rol> perfiles
) {
}
