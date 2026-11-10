package jdev.mentoria.lojavirtual.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jdev.mentoria.lojavirtual.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByLogin(String login);
	
	Optional<Usuario> findByEmpresaIdAndLogin(Long id, String login);
	
	@Query(value = "select u from Usuario u where u.dataSenhaAtual <= :dataLimite")
	List<Usuario> listarUsuariosComSenhasVencidas(LocalDate dataLimite);

}
