package com.aluracursos.challenge_forohub.domain.curso;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum Categoria {
    @JsonAlias("Bug") BUG,
    @JsonAlias("Duda") DUDA,
    @JsonAlias("Proyecto") PROYECTO,
    @JsonAlias("Queja") QUEJA,
    @JsonAlias("Sugerencia") SUGERENCIA
}
