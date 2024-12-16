package com.aluracursos.challenge_forohub.domain.perfil;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    List<Perfil> findByNombreIn(List<Rol> nombresRoles);
}
