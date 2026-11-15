package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.AvaliacaoProduto;

@Transactional
public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, Long> {
	
	List<AvaliacaoProduto> findByProdutoId(Long produtoId);
	
	List<AvaliacaoProduto> findByProdutoIdAndPessoaId(Long produtoId, Long pessoaId);
	
	List<AvaliacaoProduto> findByPessoaId(Long pessoaId);

}
