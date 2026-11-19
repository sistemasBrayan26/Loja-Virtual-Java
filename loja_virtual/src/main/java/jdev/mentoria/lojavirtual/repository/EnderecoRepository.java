package jdev.mentoria.lojavirtual.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.Endereco;

@Transactional
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
	

}
