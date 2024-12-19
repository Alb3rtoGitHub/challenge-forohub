package com.aluracursos.challenge_forohub.infra.errores;

import com.aluracursos.challenge_forohub.infra.security.JWTException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException ex) {
        var errores = ex.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity tratarErrorDuplicados(IllegalArgumentException ex) {
//        var errores = ex.getMessage();
//        return ResponseEntity.badRequest().body(errores);
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarError403(IllegalArgumentException ex) {
        var errores = ex.getMessage();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errores);
    }

    @ExceptionHandler(JWTException.class)
    public ResponseEntity tratarJWTException(JWTException ex) {
        return ResponseEntity.status(401).body("Error de autenticación: " + ex.getMessage());
    }

    private record DatosErrorValidacion(String campo, String error) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }


}
