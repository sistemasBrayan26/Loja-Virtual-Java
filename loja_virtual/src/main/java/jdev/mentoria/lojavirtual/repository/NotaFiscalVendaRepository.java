package jdev.mentoria.lojavirtual.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jdev.mentoria.lojavirtual.model.NotaFiscalVenda;

public interface NotaFiscalVendaRepository extends JpaRepository<NotaFiscalVenda, Long> {
	
	Optional<NotaFiscalVenda> findByNumero(String numero);
	
	Optional<NotaFiscalVenda> findByNumeroAndSerie(String numero, String serie);
	
	List<NotaFiscalVenda> findByNumeroContainingIgnoreCase(String numero);
	
	List<NotaFiscalVenda> findByEmpresaId(Long empresaId);
	
	Optional<NotaFiscalVenda> findByVendaCompraLojaVirtualId(Long vendaCompraLojaVirtualId);

}
