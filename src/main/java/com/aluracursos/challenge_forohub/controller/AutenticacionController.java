package com.aluracursos.challenge_forohub.controller;

import com.aluracursos.challenge_forohub.domain.usuario.DatosAutenticacionUsuario;
import com.aluracursos.challenge_forohub.domain.usuario.Usuario;
import com.aluracursos.challenge_forohub.infra.security.DatosJWTtoken;
import com.aluracursos.challenge_forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.usuario(), datosAutenticacionUsuario.contrasena()); // me pide usuario y clave
        var usuarioAutenticado = authenticationManager.authenticate(authToken);// el metodo authenticate espera un objeto tipo authentication (token)
        var JWTtoken = tokenService.generateToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTtoken(JWTtoken));
    }
}
