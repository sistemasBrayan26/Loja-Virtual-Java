package jdev.mentoria.lojavirtual.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jdev.mentoria.lojavirtual.model.Acesso;

public interface AcessoRepository extends JpaRepository<Acesso, Long> {
	
	Optional<Acesso> findByDescricaoAndEmpresaId(String descricao, Long empresaId);
	
	Optional<Acesso> findByDescricao(String descricao);

}
