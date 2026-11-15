package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jdev.mentoria.lojavirtual.model.ImagemProduto;

public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, Long> {
	
	List<ImagemProduto> findByProdutoId(Long produtoId);
	
	void deleteByProdutoId(Long produtoId);

}
