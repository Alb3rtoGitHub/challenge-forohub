package com.aluracursos.challenge_forohub.controller;


import com.aluracursos.challenge_forohub.domain.perfil.Perfil;
import com.aluracursos.challenge_forohub.domain.perfil.PerfilRepository;
import com.aluracursos.challenge_forohub.domain.usuario.DatosRegistroUsuario;
import com.aluracursos.challenge_forohub.domain.usuario.DatosRespuestaUsuario;
import com.aluracursos.challenge_forohub.domain.usuario.Usuario;
import com.aluracursos.challenge_forohub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario, UriComponentsBuilder uriComponentsBuilder) {
        // Validar si el usuario ya existe por correo electrònico
        usuarioRepository.findUsuarioByCorreoElectronico(datosRegistroUsuario.correoElectronico())
                .ifPresent(usuario -> {
                    throw new IllegalArgumentException("Ya Existe un usuario con el mismo correo electrónico.");
                });

        // Encriptar constaseña usando BCrypt
        String passwordEncriptado = passwordEncoder.encode(datosRegistroUsuario.contrasena());

        // Buscar perfiles en la BD
        List<Perfil> perfiles = perfilRepository.findByNombreIn(datosRegistroUsuario.perfiles());

        // Crear un usuario
        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario, passwordEncriptado, perfiles));
        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(usuario);

        //URL donde encontrar el topico
        URI uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(datosRespuestaUsuario); // Retorna el 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {//TODO usar DTO
        return usuarioRepository.findById(id).map(u -> {
            //usar DTO
//            u.setNombre(usuario.getNombre());
//            u.setCorreoElectronico(usuario.getCorreoElectronico());
            return ResponseEntity.ok(usuarioRepository.save(u));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
