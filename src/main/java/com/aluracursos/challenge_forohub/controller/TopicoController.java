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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public void registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        // Buscar si el curso ya existe en la base de datos
        Curso cursoExiste = cursoRepository.findByNombreCursoAndCategoria(
                datosRegistroTopico.datosCurso().nombreCurso(),
                datosRegistroTopico.datosCurso().categoria()
        ).orElseGet(() -> {
            // Si el curso no existe, crearlo
            Curso cursoNuevo = new Curso(datosRegistroTopico.datosCurso());
            return cursoRepository.save(cursoNuevo);
        });

        // Crear el objeto Topico con el curso persistido para evitar el error de Curso no persistido antes de Topico
        topicoRepository.save(new Topico(datosRegistroTopico, cursoExiste));
    }

    // Agrego paginación y ordenamiento por fecha de creación ascendente
    @GetMapping
    public Page<DatosListadoTopico> listadoTopicos(@PageableDefault(page = 0, size = 10, sort = {"fechaDeCreacion"}) Pageable paginacion) {
//        return topicoRepository.findAll(paginacion).map(DatosListadoTopico::new);
        return topicoRepository.findByActivoTrue(paginacion).map(DatosListadoTopico::new);
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
    public DatosRespuestaTopico retornaDatosTopico(@PathVariable("id") Long id) {
        // Verificación del ID no nulo y valor positivo
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id " + id + " no existe." );
        }

        Topico topico = topicoRepository.findById(id).orElse(null);
        if (topico == null) {
            throw new IllegalArgumentException("El topico " + id + " no existe." );
        }

        // Buscar el tópico por ID
        return new DatosRespuestaTopico(topico);
    }
    // a futuro implementar esto y borrar lo anterior:
//    public ResponseEntity<DatosListadoTopico> buscarPorId(@PathVariable("id") Long id) {
//        // Verificación de que el ID es positivo
//        if (id == null || id <= 0) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(null);  // Devuelve 400 si el ID es nulo o no positivo
//        }
//
//        // Buscar el tópico por ID
//        return topicoRepository.findById(id)
//                .map(topico -> ResponseEntity.ok(new DatosListadoTopico(topico)))  // Si se encuentra el tópico
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Si no se encuentra el tópico
//    }

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
    }

}
