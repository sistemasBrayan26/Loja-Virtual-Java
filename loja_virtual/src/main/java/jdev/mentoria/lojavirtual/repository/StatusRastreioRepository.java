package jdev.mentoria.lojavirtual.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jdev.mentoria.lojavirtual.model.StatusRastreio;

public interface StatusRastreioRepository extends JpaRepository<StatusRastreio, Long> {
	
	List<StatusRastreio> findByVendaCompraLojaVirtualId(Long vendaId);

}
