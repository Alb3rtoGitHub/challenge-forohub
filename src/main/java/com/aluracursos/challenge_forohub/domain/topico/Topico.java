package com.aluracursos.challenge_forohub.domain.topico;

import com.aluracursos.challenge_forohub.domain.curso.Curso;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
    private LocalDateTime fechaDeCreacion = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatusTopico status = StatusTopico.SIN_RESPUESTA;

    private String autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
//    @JsonManagedReference
    private Curso curso;

    public Topico(DatosRegistroTopico datosRegistroTopico, Curso curso) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.autor = datosRegistroTopico.autor();
        this.curso = curso;
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {
        if (datosActualizarTopico.titulo() != null) {
            this.titulo = datosActualizarTopico.titulo();
        }

        //TODO
        // Hacer lo mismo con el resto...
        this.mensaje = datosActualizarTopico.mensaje();
        this.fechaDeCreacion = datosActualizarTopico.fechaDeCreacion();
        this.status = datosActualizarTopico.statusTopico();
        this.autor = datosActualizarTopico.autor();
        this.curso = curso.actualizarDatosCurso(datosActualizarTopico);

    }
}
