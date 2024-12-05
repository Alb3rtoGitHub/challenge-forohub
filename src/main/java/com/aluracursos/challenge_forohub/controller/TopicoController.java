package com.aluracursos.challenge_forohub.controller;

import com.aluracursos.challenge_forohub.domain.curso.Curso;
import com.aluracursos.challenge_forohub.domain.curso.CursoRepository;
import com.aluracursos.challenge_forohub.domain.curso.NombreCurso;
import com.aluracursos.challenge_forohub.domain.topico.DatosListadoTopico;
import com.aluracursos.challenge_forohub.domain.topico.DatosRegistroTopico;
import com.aluracursos.challenge_forohub.domain.topico.Topico;
import com.aluracursos.challenge_forohub.domain.topico.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
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
        return topicoRepository.findAll(paginacion)
                .map(DatosListadoTopico::new);
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
    public DatosListadoTopico buscarPorId(@PathVariable("id") Long id) {
        // Verificación del ID no nulo y valor positivo
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id " + id + " no existe." );
        }

        Topico topico = topicoRepository.findById(id).orElse(null);
        if (topico == null) {
            throw new IllegalArgumentException("El topico " + id + " no existe." );
        }
        var datosTopico = new DatosListadoTopico(topico);

        // Buscar el tópico por ID
        return datosTopico;
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
}
