package jdev.mentoria.lojavirtual.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdev.mentoria.lojavirtual.ApplicationContextLoad;
import jdev.mentoria.lojavirtual.model.Usuario;
import jdev.mentoria.lojavirtual.repository.UsuarioRepository;

@Service
public class JWTTokenAutenticacaoService {

	private static final long EXPIRATION_TIME = 959990000;
	private static final String SECRET = "ss/-*-*sds565dsd-s/d-s*dsds_sua_chave_secreta_bem_longa_e_segura_para_hs512_aqui_123456789";
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";
	
	public String gerarToken(String username) {
        return Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }
	
	public Authentication getAuthetication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
            try {
                var payload = Jwts.parser()
                        .verifyWith(getSigningKey())
                        .build()
                        .parseSignedClaims(tokenLimpo)
                        .getPayload();

                String user = payload.getSubject();

                if (user != null) {
                    UsuarioRepository usuarioRepository = ApplicationContextLoad
                            .getApplicationContext().getBean(UsuarioRepository.class);
                    
                    Optional<Usuario> usuario = usuarioRepository.findByLogin(user);

                    if (usuario.isPresent()) {
                        return new UsernamePasswordAuthenticationToken(
                                usuario.get().getLogin(),
                                null,
                                usuario.get().getAuthorities()
                        );
                    }
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
	
	private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }


}
