package com.aluracursos.challenge_forohub.domain.curso;

import com.aluracursos.challenge_forohub.domain.topico.Topico;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated(EnumType.STRING)
        private NombreCurso nombreCurso;

        @Enumerated(EnumType.STRING)
        private Categoria categoria;

        @OneToMany(mappedBy = "curso")
        @JsonBackReference
        private List<Topico> topicos;

        public Curso(DatosCurso datosCurso) {
                this.nombreCurso = datosCurso.nombreCurso();
                this.categoria = datosCurso.categoria();
        }
}
