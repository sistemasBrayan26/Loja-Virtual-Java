package jdev.mentoria.lojavirtual.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.model.Pessoa;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	Optional<PessoaJuridica> findByCnpj(String cnpj);

}
