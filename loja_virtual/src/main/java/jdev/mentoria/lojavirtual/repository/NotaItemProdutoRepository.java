package jdev.mentoria.lojavirtual.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jdev.mentoria.lojavirtual.model.NotaItemProduto;

public interface NotaItemProdutoRepository extends JpaRepository<NotaItemProduto, Long> {
	
	List<NotaItemProduto> findByNotaFiscalCompraId(Long compraId);
	
	Optional<NotaItemProduto> findByProdutoIdAndNotaFiscalCompraId(Long produtoId, Long notaFiscalCompraId);
	
	List<NotaItemProduto> findByProdutoId(Long produtoId);
	
	List<NotaItemProduto> findByEmpresaId(Long empresaId);
	
	

}
