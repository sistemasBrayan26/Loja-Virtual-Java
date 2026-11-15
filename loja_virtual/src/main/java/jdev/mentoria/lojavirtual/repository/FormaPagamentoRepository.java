package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.FormaPagamento;

@Transactional
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
	
	List<FormaPagamento> findByDescricao(String descricao);

}
