package com.aluracursos.challenge_forohub.domain.usuario;

import com.aluracursos.challenge_forohub.domain.perfil.Perfil;
import com.aluracursos.challenge_forohub.domain.perfil.Rol;
import com.aluracursos.challenge_forohub.domain.respuesta.Respuesta;
import com.aluracursos.challenge_forohub.domain.topico.Topico;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "correo_electronico", nullable = false, unique = true)
    private String correoElectronico;

    private String contrasena;

    @ManyToMany(fetch = FetchType.EAGER) // Los perfiles se cargan con el usuario
    @JoinTable(
            name = "usuarios_perfiles",
            joinColumns = @JoinColumn(name = "usuarios_id"),
            inverseJoinColumns = @JoinColumn(name = "perfiles_id")
    )
    private List<Perfil> perfiles = new ArrayList<>();

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Respuesta> respuestas = new ArrayList<>();

    public Usuario(DatosRegistroUsuario datosRegistroUsuario, String passwordEncriptado, List<Perfil> perfiles) {
        this.nombre = datosRegistroUsuario.nombre();
        this.correoElectronico = datosRegistroUsuario.correoElectronico();
        this.contrasena = passwordEncriptado;
        this.perfiles = perfiles;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));

        // Posible mejora?
//        return perfiles.stream()
//                .map(perfil -> new SimpleGrantedAuthority(perfil.getNombre().name()))
//                .toList();
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correoElectronico;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void actualizarDatosUsuario(DatosActualizarUsuario datosActualizarUsuario, List<Perfil> nuevosPerfiles, String passwordEncriptado) {
        if (datosActualizarUsuario.correoElectronico() != null) {
            this.correoElectronico = datosActualizarUsuario.correoElectronico();
        }

        if (passwordEncriptado != null) {
            this.contrasena = passwordEncriptado;
        }

        if (datosActualizarUsuario.perfiles() != null) {
            this.perfiles = nuevosPerfiles;
        }
    }
}
