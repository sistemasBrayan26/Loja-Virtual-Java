package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.VendaCompraLojaVirtual;

public interface VendaCompraLojaVirtualRepository extends JpaRepository<VendaCompraLojaVirtual, Long> {
	
	List<VendaCompraLojaVirtual> findByPessoaId(Long pessoaId);
	
	@Transactional
	@Modifying
	@Query(value = "begin; update nota_fiscal_venda set venda_compra_loja_virtual_id = null where "
			+ "venda_compra_loja_virtual_id = ?1; "
			+ "delete from nota_fiscal_venda where venda_compra_loja_virtual_id = ?1;"
			+ "delete from item_venda_loja where venda_compra_loja_virtual_id = ?1;"
			+ "delete from status_rastreio where venda_compra_loja_virtual_id = ?1;"
			+ "delete from venda_compra_loja_virtual where id = ?1; commit;", nativeQuery = true)
	void exclusaoTotalVendaBanco(Long vendaId);

}
