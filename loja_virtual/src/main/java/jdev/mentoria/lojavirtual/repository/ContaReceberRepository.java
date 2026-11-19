package jdev.mentoria.lojavirtual.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.ContaPagar;
import jdev.mentoria.lojavirtual.model.ContaReceber;

@Transactional
public interface ContaReceberRepository extends JpaRepository<ContaReceber, Long> {
	
	List<ContaReceber> findByDescricaoContainingIgnoreCase(String descricao);
	
	List<ContaReceber> findByPessoaId(Long pessoaId);
	
	List<ContaReceber> findByEmpresaId(Long empresaId);

}
