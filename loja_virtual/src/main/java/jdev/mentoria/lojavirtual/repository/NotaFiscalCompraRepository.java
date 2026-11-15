package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jdev.mentoria.lojavirtual.model.NotaFiscalCompra;

public interface NotaFiscalCompraRepository extends JpaRepository<NotaFiscalCompra, Long> {
	
	List<NotaFiscalCompra> findByObservacaoContainingIgnoreCase(String observacao);
	
	List<NotaFiscalCompra> findByPessoaId(Long pessoaId);
	
	List<NotaFiscalCompra> findByContaPagarId(Long contaPagarId);
	
	List<NotaFiscalCompra> findByNumeroNotaContaining(String numero);
	
	List<NotaFiscalCompra> findByEmpresaId(Long empresaId);

}
