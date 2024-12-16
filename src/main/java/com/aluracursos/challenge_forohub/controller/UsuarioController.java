package com.aluracursos.challenge_forohub.controller;


import com.aluracursos.challenge_forohub.domain.usuario.Usuario;
import com.aluracursos.challenge_forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Usuario>  registrarUsuario(@RequestBody Usuario usuario) { //TODO usar DTO
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario>  actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {//TODO usar DTO
        return usuarioRepository.findById(id).map(u -> {
            //usar DTO
//            u.setNombre(usuario.getNombre());
//            u.setCorreoElectronico(usuario.getCorreoElectronico());
            return ResponseEntity.ok(usuarioRepository.save(u));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario>  eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
