package com.aluracursos.challenge_forohub.controller;

import com.aluracursos.challenge_forohub.domain.respuesta.Respuesta;
import com.aluracursos.challenge_forohub.domain.respuesta.RespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @GetMapping
    public List<Respuesta> listarRespuestas() {
        return respuestaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Respuesta> crearRespuesta(@RequestBody Respuesta respuesta) {
        return ResponseEntity.ok(respuestaRepository.save(respuesta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Respuesta> actualizarRespuesta(@PathVariable Long id, @RequestBody Respuesta respuesta) {
        return respuestaRepository.findById(id).map(r -> {
//            r.setMensaje(respuesta.getMensaje());
//            r.setSolucion(respuesta.getSolucion());
            return ResponseEntity.ok(respuestaRepository.save(r));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long id) {
        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
