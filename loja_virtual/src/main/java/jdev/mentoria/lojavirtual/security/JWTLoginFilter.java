package jdev.mentoria.lojavirtual.security;

import java.io.IOException;

import javax.naming.AuthenticationException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdev.mentoria.lojavirtual.model.Usuario;
import tools.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
        super(url);
        setAuthenticationManager(authenticationManager);
    }
	
	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        // Obtém o usuário enviado no corpo da requisição JSON de login
        Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
        
        // Retorna o token de autenticação contendo login e senha para validação
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha())
        );
    }
	
	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
    		FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        try {
            // Se a autenticação for bem-sucedida, gera e adiciona o token JWT na resposta
            new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        
        // Configura o status e o tipo de conteúdo ANTES de escrever no writer
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        
        String mensagemErro;
        if (failed.getMessage() != null && (failed.getMessage().toLowerCase().contains("bad credentials") 
        		|| failed.getMessage().toLowerCase().contains("badcredentials"))) {
            mensagemErro = "Usuário ou senha inválidos";
        } else {
            mensagemErro = "Falha ao logar: " + failed.getMessage();
        }
        
        response.getWriter().write("{\"erro\": \"" + mensagemErro + "\"}");
        response.getWriter().flush();
    }

}
