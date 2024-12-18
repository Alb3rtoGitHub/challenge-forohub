package com.aluracursos.challenge_forohub.controller;


import com.aluracursos.challenge_forohub.domain.perfil.Perfil;
import com.aluracursos.challenge_forohub.domain.perfil.PerfilRepository;
import com.aluracursos.challenge_forohub.domain.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Page<DatosListadoUsuario>> listadoUsuarios(@PageableDefault(page = 0, size = 10, sort = {"nombre"}) Pageable pageable) {
        return ResponseEntity.ok(usuarioRepository.findAll(pageable).map(DatosListadoUsuario::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaUsuario> retornaDatosUsuarios(@PathVariable Long id) {
        // Verificación de que el ID es positivo
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);  // Devuelve 400 si el ID es nulo o negativo
        }

        return usuarioRepository.findById(id)
                .map(usuario -> ResponseEntity.ok(new DatosRespuestaUsuario(usuario)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/buscar")
    public Page<DatosListadoUsuario> buscarPorNombreOCorreoElectronico(@RequestParam(required = false) String nombre, @RequestParam(required = false) String correo, @PageableDefault(size = 10, sort = "nombre") Pageable pageable) {
        return usuarioRepository.buscarPorNombreOCorreoElectronico(nombre, correo, pageable)
                .map(DatosListadoUsuario::new);
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

        //URL donde encontrar el topico
        URI uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosRespuestaUsuario(usuario)); // Retorna el 201 Created
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
        // Validar si el ID es válido
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id proporcionado no es válido: " + id);
        }

        // Buscar el usuario con Optional
        var usuarioOptional = usuarioRepository.findById(id);
        if (!usuarioOptional.isPresent()) {
            throw new IllegalArgumentException("El usuario con ID " + id + " no existe.");
        }

        Usuario usuario = usuarioOptional.get();

        // Encriptar constaseña usando BCrypt solo si está presente
        String passwordEncriptado = null;
        if (datosActualizarUsuario.contrasena() != null) {
            passwordEncriptado = passwordEncoder.encode(datosActualizarUsuario.contrasena());
        }

        // Buscar perfiles en la BD
        List<Perfil> nuevosPerfiles = perfilRepository.findByNombreIn(datosActualizarUsuario.perfiles());

        usuario.actualizarDatosUsuario(datosActualizarUsuario, nuevosPerfiles, passwordEncriptado);
        return ResponseEntity.ok(new DatosRespuestaUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Usuario> eliminarUsuario(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id " + id + " no existe.");
        }
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (!optionalUsuario.isPresent()) {
            throw new IllegalArgumentException("El usuario con id " + id + " no existe.");
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
