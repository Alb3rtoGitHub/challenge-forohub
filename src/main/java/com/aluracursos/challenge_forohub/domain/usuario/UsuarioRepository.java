package com.aluracursos.challenge_forohub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByCorreoElectronico(String username);

    String nombre(String nombre);

    Optional<Usuario> findUsuarioByCorreoElectronico(String correoElectronico);
}
