package com.aluracursos.challenge_forohub.domain.respuesta;

import com.aluracursos.challenge_forohub.domain.topico.Topico;
import com.aluracursos.challenge_forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario autor;

    private boolean solucion = false;

    public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta, Usuario autor, Topico topico) {
        this.mensaje = datosRegistroRespuesta.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.autor = autor;
        this.topico = topico;
        this.solucion = false;
    }

    public void actualizarDatosRespuesta(DatosActualizarRespuesta datosActualizarRespuesta) {
        if (datosActualizarRespuesta.mensaje() != null) {
            this.mensaje = datosActualizarRespuesta.mensaje();
        }
    }

    public void setSolucion(Boolean solucion) {
        this.solucion = solucion;
    }
}
