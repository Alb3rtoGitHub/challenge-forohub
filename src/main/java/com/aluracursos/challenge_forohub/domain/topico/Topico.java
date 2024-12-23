package com.aluracursos.challenge_forohub.domain.topico;

import com.aluracursos.challenge_forohub.domain.curso.Curso;
import com.aluracursos.challenge_forohub.domain.respuesta.Respuesta;
import com.aluracursos.challenge_forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario autor;

//    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "curso_id")
//    @JsonManagedReference
    private Curso curso;

    @OneToMany(mappedBy = "topico", fetch = FetchType.EAGER)
    private List<Respuesta> respuestas;

    public Topico(DatosRegistroTopico datosRegistroTopico, Curso curso, Usuario autor) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.autor = autor;
//        this.activo = true;
        this.curso = curso;
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico, Usuario autorActualizado) {
        if (datosActualizarTopico.titulo() != null) {
            this.titulo = datosActualizarTopico.titulo();
        }

        if (datosActualizarTopico.mensaje() != null) {
            this.mensaje = datosActualizarTopico.mensaje();
        }

        if (datosActualizarTopico.fechaDeCreacion() != null) {
            this.fechaDeCreacion = datosActualizarTopico.fechaDeCreacion();
        }

        if (datosActualizarTopico.statusTopico() != null) {
            this.status = datosActualizarTopico.statusTopico();
        }
        if (autorActualizado != null) {
            this.autor = autorActualizado;
        }

        if (datosActualizarTopico.datosCurso() != null) {
            this.curso = curso.actualizarDatosCurso(datosActualizarTopico);
        }

    }

    public void cambiarStatus(StatusTopico statusTopico) {
        this.status = statusTopico;
    }

    public void actualizarSolucionado() {
        this.status = StatusTopico.RESUELTO;
    }

    // Para borrado lógico
//    public void desactivarTopico() {
//        this.activo = false;
//    }
}
