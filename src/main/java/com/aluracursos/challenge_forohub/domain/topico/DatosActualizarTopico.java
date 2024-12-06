package com.aluracursos.challenge_forohub.domain.topico;

import com.aluracursos.challenge_forohub.domain.curso.DatosCurso;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosActualizarTopico(
        @NotNull
        Long id,

        @NotBlank
        String titulo,

        @NotBlank
        String mensaje,

        @NotNull @Valid
        LocalDateTime fechaDeCreacion,

        @NotNull
        StatusTopico statusTopico,

        @NotBlank
        String autor,

        @NotNull
        @Valid
        @JsonProperty("curso") DatosCurso datosCurso
) {}
