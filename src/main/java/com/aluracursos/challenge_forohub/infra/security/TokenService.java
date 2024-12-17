package com.aluracursos.challenge_forohub.infra.security;

import com.aluracursos.challenge_forohub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("api.security.secret")
    private String apiSecret; // toma el valor de application.properties

    // Generar token
    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("aluracursos")
                    .withSubject(usuario.getCorreoElectronico())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
//            throw new RuntimeException("Error al generar el token JWT", exception);
            throw new JWTException("Error al generar el token JWT", exception);
        }
    }

    // Validar Token
    public String getSubject(String token) {
        // Valido si el token es nulo
        if (token == null) {
//            throw new RuntimeException("Se ha proporcionado un Token nulo");
            throw new JWTException("El token no puede ser nulo.");
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // validando firma del token
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("aluracursos")
                    .build()
                    .verify(token);
            String subject = verifier.getSubject();
            if (subject == null) {
//                throw new RuntimeException("Se token no contiene un subject válido");
                throw new JWTException("El token no contiene un subject válido.");
            }
            return subject;
        } catch (JWTVerificationException exception){
            // Manejar el caso de token expirado o inválido
//            throw new RuntimeException("Error al verificar el token: " + exception.getMessage(), exception);
            throw new JWTException("Error al verificar el token JWT: " + exception.getMessage(), exception);
        }
//        if(verifier.getSubject() == null){
//            throw new RuntimeException("Verifier inválido");
//        }
//        return verifier.getSubject();
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(5).toInstant(ZoneOffset.of("-03:00"));
    }
}
