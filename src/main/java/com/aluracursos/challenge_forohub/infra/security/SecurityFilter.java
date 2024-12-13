package com.aluracursos.challenge_forohub.infra.security;

import com.aluracursos.challenge_forohub.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtengo el token del header
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            var nombreUsuario = tokenService.getSubject(token); // Este usuario tiene sesion?, extraemos el nombre de usuario
            if (nombreUsuario != null) {
                // Token válido => debo forzar un inicio de sesión en mi sistema
                var usuario = usuarioRepository.findByCorreoElectronico(nombreUsuario); // Encuentro el usuario
                // Con esto le dijo a Spring que el login es válido
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); // Forzamos inicio sesion
                // Invoco clase SecurityContextHolder de Spring...necesita un objeto authentication
                SecurityContextHolder.getContext().setAuthentication(authentication); // seteamos manual la autenticación, le digo a Spring que el usuario esta autenticado
            }
        }

        filterChain.doFilter(request, response); // esto envía la cadena de filtro siguiente para que llegue el request al controller
    }
}
