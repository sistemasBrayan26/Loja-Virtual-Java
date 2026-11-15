package jdev.mentoria.lojavirtual.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.ContaPagar;

@Transactional
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {
	
	List<ContaPagar> findByDescricaoContainingIgnoreCase(String descricao);
	
	List<ContaPagar> findByPessoaId(Long pessoaId);
	
	List<ContaPagar> findByPessoaFornecedoraId(Long pessoaFornecedoraId);
	
	List<ContaPagar> findByEmpresaId(Long empresaId);

}
