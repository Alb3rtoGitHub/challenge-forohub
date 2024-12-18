package com.aluracursos.challenge_forohub.controller;

import com.aluracursos.challenge_forohub.domain.respuesta.*;
import com.aluracursos.challenge_forohub.domain.topico.TopicoRepository;
import com.aluracursos.challenge_forohub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping
    public ResponseEntity<Page<DatosListadoRespuesta>> listarRespuestas(@PageableDefault(page = 0, size = 10, sort = {"fechaCreacion"}) Pageable pageable) {
        return ResponseEntity.ok(respuestaRepository.findAll(pageable).map(DatosListadoRespuesta::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaRespuesta> retornaDatosRespuesta(@PathVariable Long id) {
        // Verificación de que el ID es positivo
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);  // Devuelve 400 si el ID es nulo o negativo
        }

        return respuestaRepository.findById(id)
                .map(respuesta -> ResponseEntity.ok(new DatosRespuestaRespuesta(respuesta)))
                .orElse(ResponseEntity.notFound().build());
    }

//TODO otros GET's

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta, UriComponentsBuilder uriComponentsBuilder) {
        // Validar si la respuesta ya existe
        respuestaRepository.findByMensajeContains(datosRegistroRespuesta.mensaje())
                .ifPresent(respuesta -> {
                    throw new IllegalArgumentException("Ya Existe una respuesta con el mismo mensaje.");
                });


        var autor = usuarioRepository.findById(datosRegistroRespuesta.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("El usuario con ID " + datosRegistroRespuesta.usuarioId() + " no existe."));

        var topico = topicoRepository.findById(datosRegistroRespuesta.topicoId())
                .orElseThrow(() -> new IllegalArgumentException("El topico con ID " + datosRegistroRespuesta.topicoId() + " no existe."));

        Respuesta respuesta = respuestaRepository.save(new Respuesta(datosRegistroRespuesta, autor, topico));

        URI uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosRespuestaRespuesta(respuesta));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaRespuesta> actualizarRespuesta(@PathVariable Long id, @RequestBody DatosActualizarRespuesta datosActualizarRespuesta) {
        // Validar si el ID es válido
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id proporcionado no es válido: " + id);
        }

        // Buscar la respuesta con Optional, si la encuentra la usamos o sino lanza Exception
        var respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El respuesta con ID " + id + " no existe."));
        respuesta.actualizarDatosRespuesta(datosActualizarRespuesta);
        return ResponseEntity.ok(new DatosRespuestaRespuesta(respuesta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Respuesta> eliminarRespuesta(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id " + id + " no existe.");
        }
        Optional<Respuesta> optionalRespuesta = respuestaRepository.findById(id);
        if (!optionalRespuesta.isPresent()) {
            throw new IllegalArgumentException("La respuesta con id " + id + " no existe.");
        }

        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
