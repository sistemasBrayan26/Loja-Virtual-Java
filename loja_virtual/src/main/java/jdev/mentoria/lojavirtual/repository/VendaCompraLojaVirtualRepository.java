package jdev.mentoria.lojavirtual.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.VendaCompraLojaVirtual;

public interface VendaCompraLojaVirtualRepository extends JpaRepository<VendaCompraLojaVirtual, Long> {
	
	List<VendaCompraLojaVirtual> findByPessoaIdAndExcluidoFalse(Long pessoaId);
	
	@Transactional
	@Modifying
	@Query(value = "begin; update nota_fiscal_venda set venda_compra_loja_virtual_id = null where "
			+ "venda_compra_loja_virtual_id = ?1; "
			+ "delete from nota_fiscal_venda where venda_compra_loja_virtual_id = ?1;"
			+ "delete from item_venda_loja where venda_compra_loja_virtual_id = ?1;"
			+ "delete from status_rastreio where venda_compra_loja_virtual_id = ?1;"
			+ "delete from venda_compra_loja_virtual where id = ?1; commit;", nativeQuery = true)
	void exclusaoTotalVendaBanco(Long vendaId);
	
	Optional<VendaCompraLojaVirtual> findByIdAndExcluidoFalse(Long id);
	
	List<VendaCompraLojaVirtual> findByItemVendaLojasProdutoIdAndExcluidoFalse(Long produtoId);

	List<VendaCompraLojaVirtual> findDistinctByItemVendaLojasProdutoNomeAndExcluidoFalse(String nomeProduto);
	
	List<VendaCompraLojaVirtual> findDistinctByPessoaNomeContainingIgnoreCaseAndExcluidoFalse(String nome);
	
	List<VendaCompraLojaVirtual> findByEnderecoCobrancaLogradouroContainingIgnoreCaseAndExcluidoFalse(String logradouro);
	
	List<VendaCompraLojaVirtual> findByEnderecoCobrancaCepContainingIgnoreCaseAndExcluidoFalse(String cep);
	
	List<VendaCompraLojaVirtual> findByEnderecoEntregaLogradouroContainingIgnoreCaseAndExcluidoFalse(String logradouro);
	
	List<VendaCompraLojaVirtual> findByEnderecoEntregaCepContainingIgnoreCaseAndExcluidoFalse(String cep);
	
	List<VendaCompraLojaVirtual> findByDataVendaBetweenAndExcluidoFalse(LocalDate dataInicial, 
			LocalDate dataFinal);
	
	List<VendaCompraLojaVirtual> findByDataEntregaBetweenAndExcluidoFalse(LocalDate dataInicial, 
			LocalDate dataFinal);
	
	

}
