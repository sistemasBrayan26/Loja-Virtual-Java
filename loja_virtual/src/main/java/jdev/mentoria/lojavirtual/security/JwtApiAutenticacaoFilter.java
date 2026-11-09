package jdev.mentoria.lojavirtual.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtApiAutenticacaoFilter extends OncePerRequestFilter {
	
	@Autowired
    private JWTTokenAutenticacaoService jwtTokenAutenticacaoService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
            // Valida e obtém a autenticação do usuário através do token enviado na requisição
            Authentication authentication = jwtTokenAutenticacaoService.getAuthetication(request, response);
            
            if (authentication != null) {
                // Insere o processo de autenticação dentro do contexto do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            
            filterChain.doFilter(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Ocorreu um erro no sistema, avise o administrador: \n" +
            e.getMessage());
        }
		
	}

}
