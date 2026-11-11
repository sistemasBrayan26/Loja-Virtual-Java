package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jdev.mentoria.lojavirtual.model.PessoaFisica;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {
	
	List<PessoaFisica> findByNomeContainingIgnoreCase(String nome);
	
	List<PessoaFisica> findByCpfContainingIgnoreCase(String cpf);

}
