package jdev.mentoria.lojavirtual.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jdev.mentoria.lojavirtual.model.Usuario;
import jdev.mentoria.lojavirtual.repository.UsuarioRepository;

@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> usuarioEncontrado = usuarioRepository.findByLogin(username);
		
		if (usuarioEncontrado.isEmpty()) {
			throw new UsernameNotFoundException("Usuário não foi encontrado.");
		}
		
		return usuarioEncontrado.get(); 
	}
	
	
	
	

}
