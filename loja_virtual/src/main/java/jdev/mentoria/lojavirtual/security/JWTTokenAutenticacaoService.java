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
	
public void addAuthentication(HttpServletResponse response, String username) throws Exception {
        
        String jwt = Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
        
        String token = TOKEN_PREFIX + " " + jwt;
        
        response.addHeader(HEADER_STRING, token);
        liberacaoCors(response);
        
        Optional<Usuario> usuario = ApplicationContextLoad.
                getApplicationContext().
                getBean(UsuarioRepository.class).findByLogin(username);
        
        if (usuario.isPresent() && usuario.get().getEmpresa() != null) {
            
            // Retorno estruturado em JSON para o cliente/Postman
            response.getWriter().write("{"
                    + "\"Authorization\": \"" + token + "\","
                    + "\"username\": \"" + username + "\","
                    + "\"id\": \"" + usuario.get().getId() + "\","
                    + "\"empresa\": \"" + usuario.get().getEmpresa().getId() + "\""
                    + "}");
        }
    }

	public Authentication getAuthetication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String token = request.getHeader(HEADER_STRING);

		try {
			if (token != null) {
				String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
				
				var payload = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(tokenLimpo)
						.getPayload();

				String user = payload.getSubject();

				if (user != null) {
					Optional<Usuario> usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class)
							.findByLogin(user);

					if (usuario.isPresent()) {
						return new UsernamePasswordAuthenticationToken(usuario.get().getLogin(), usuario.get().getSenha(),
								usuario.get().getAuthorities());
					}
				}
			}
		} catch (SignatureException e) {
			response.getWriter().write("Token está inválido.");
		} catch (ExpiredJwtException e) {
			response.getWriter().write("Token está expirado, efetue o login novamente.");
		} catch (JwtException e) {
			response.getWriter().write("Erro ao processar o token JWT: " + e.getMessage());
		} finally {
			liberacaoCors(response);
		}

		return null;
	}

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	}

	private void liberacaoCors(HttpServletResponse response) {
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}
		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}
		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}

}
