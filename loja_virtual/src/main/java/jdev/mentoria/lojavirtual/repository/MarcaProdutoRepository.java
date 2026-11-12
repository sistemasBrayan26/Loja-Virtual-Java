package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jdev.mentoria.lojavirtual.model.MarcaProduto;

public interface MarcaProdutoRepository extends JpaRepository<MarcaProduto, Long> {
	
	List<MarcaProduto> findByDescricaoContainingIgnoreCase(String descricao);

}
