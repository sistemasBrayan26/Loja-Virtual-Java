package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jdev.mentoria.lojavirtual.model.PessoaJuridica;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {
	
	List<PessoaJuridica> findByNomeContainingIgnoreCase(String nome);
	
	List<PessoaJuridica> findByCnpjContainingIgnoreCase(String cnpj);

}
