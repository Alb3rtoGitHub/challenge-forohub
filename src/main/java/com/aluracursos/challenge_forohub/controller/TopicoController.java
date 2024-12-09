package com.aluracursos.challenge_forohub.controller;

import com.aluracursos.challenge_forohub.domain.curso.Curso;
import com.aluracursos.challenge_forohub.domain.curso.CursoRepository;
import com.aluracursos.challenge_forohub.domain.curso.NombreCurso;
import com.aluracursos.challenge_forohub.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {
        // Validar si el topico ya existe
        topicoRepository.findByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje())
                .ifPresent(topico -> {
                    throw new IllegalArgumentException("Existe el tópico con el mismo título y mensaje.");
                });

        // Buscar si el curso ya existe en la base de datos
        Curso cursoExiste = cursoRepository.findByNombreCursoAndCategoria(
                datosRegistroTopico.datosCurso().nombreCurso(),
                datosRegistroTopico.datosCurso().categoria()
        ).orElseGet(() -> {
            // Si el curso no existe, crearlo
            Curso cursoNuevo = new Curso(datosRegistroTopico.datosCurso());
            return cursoRepository.save(cursoNuevo);
        });
        // Deberá retornar código 201 Created y la URL donde encontrar el topico ej: con GET a http://localhost:8080/topicos/id
        // Crear el objeto Topico con el curso persistido para evitar el error de Curso no persistido antes de Topico
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, cursoExiste));
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico);

        //URL donde encontrar el topico
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(datosRespuestaTopico);
    }

    // Agrego paginación y ordenamiento por fecha de creación ascendente
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(page = 0, size = 10, sort = {"fechaDeCreacion"}) Pageable paginacion) {
//        return topicoRepository.findAll(paginacion).map(DatosListadoTopico::new);
        return ResponseEntity.ok(topicoRepository.findByActivoTrue(paginacion).map(DatosListadoTopico::new));
    }

    @GetMapping("/buscar")
    public Page<DatosListadoTopico> buscarPorCursoYAnio(
            @RequestParam String nombreCurso, // Cambiado a String
            @RequestParam Integer anio,
            @PageableDefault(size = 10, sort = "fechaDeCreacion") Pageable paginacion) {

        // Cambiar String a Enum
        NombreCurso nombreCursoEnum;
        try {
            nombreCursoEnum = NombreCurso.valueOf(nombreCurso.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El curso especificado no existe: " + nombreCurso);
        }

        return topicoRepository.buscarPorCursoYAnio(nombreCursoEnum, anio, paginacion)
                .map(DatosListadoTopico::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> retornaDatosTopicos(@PathVariable("id") Long id) {
        // Verificación de que el ID es positivo
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);  // Devuelve 400 si el ID es nulo o negativo
        }

        // Buscar el tópico por ID
        return topicoRepository.findById(id)
                .map(topico -> ResponseEntity.ok(new DatosRespuestaTopico(topico)))  // Si se encuentra el tópico
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Si no se encuentra el tópico
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable("id") Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico, @PageableDefault(size = 10, sort = "fechaDeCreacion") Pageable paginacion) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id " + id + " no existe." );
        }
//        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
//        if (!topico.getActivo()) {
//            throw new IllegalArgumentException("El topico " + datosActualizarTopico.id() + " no esta activo.");
//        }

        //TODO
        // Ver si esto es mejor o no
        Topico topico = topicoRepository.getTopicoByIdAndActivoIsTrue(id);
        if (topico == null) {
            throw new IllegalArgumentException("El topico " + id + " no existe." );
        }
        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id " + id + " no existe." );
        }

        // DELETE Lógico
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build(); // retorna el código 204

        // Para DELETE de BD borrado del registro
//        Topico topico = topicoRepository.getReferenceById(id);
//        topicoRepository.delete(topico);
//        return ResponseEntity.noContent().build(); // retorna el código 204
    }

}
