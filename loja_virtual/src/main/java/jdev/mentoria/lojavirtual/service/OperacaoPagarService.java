package jdev.mentoria.lojavirtual.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jdev.mentoria.lojavirtual.ExceptionMentoria;
import jdev.mentoria.lojavirtual.model.ContaPagar;
import jdev.mentoria.lojavirtual.repository.ContaPagarRepository;

@Service
public class OperacaoPagarService {
	
	@Autowired
	private ContaPagarRepository repositorio;
	
	public ContaPagar emitir(ContaPagar contaPagar) {
		
		return repositorio.save(contaPagar);
		
	}
	
	public void cancelar(Long id) throws ExceptionMentoria {
		Optional<ContaPagar> conta = repositorio.findById(id);
		
		if (conta.isEmpty()) {
			throw new ExceptionMentoria("Não foi encontrado nenhum documento a pagar.");
		}
		
		repositorio.delete(conta.get());
	}

}
