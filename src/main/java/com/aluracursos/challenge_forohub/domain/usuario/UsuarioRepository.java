package com.aluracursos.challenge_forohub.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByCorreoElectronico(String username);

    String nombre(String nombre);

    Optional<Usuario> findUsuarioByCorreoElectronico(String correoElectronico);

    // Para coincidencia exacta...
//    @Query("""
//            SELECT u FROM  Usuario u WHERE u.nombre = :nombre OR u.correoElectronico = :correo
//            """)
@Query("""
        SELECT u FROM Usuario u 
        WHERE (:nombre IS NULL OR u.nombre LIKE %:nombre%) 
        AND (:correo IS NULL OR u.correoElectronico LIKE %:correo%)
    """)
    Page<Usuario> buscarPorNombreOCorreoElectronico(
            String nombre,
            String correo,
            Pageable pageable
    );
}
