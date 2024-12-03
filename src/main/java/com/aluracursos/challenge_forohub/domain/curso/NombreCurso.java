package com.aluracursos.challenge_forohub.domain.curso;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum NombreCurso {
    @JsonAlias("Java") JAVA,
    @JsonAlias("Java OO") JAVA_OO,
    @JsonAlias("Java y JPA") JAVA_Y_JPA,
    @JsonAlias("Spring MVC") SPRING_MVC,
    @JsonAlias("Spring Boot 3") SPRING_BOOT_3,
    @JsonAlias("Spring AI") SPRING_AI
}
