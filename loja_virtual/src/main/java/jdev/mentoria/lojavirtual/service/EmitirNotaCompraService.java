package jdev.mentoria.lojavirtual.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jdev.mentoria.lojavirtual.ExceptionMentoria;
import jdev.mentoria.lojavirtual.model.ContaPagar;
import jdev.mentoria.lojavirtual.model.NotaFiscalCompra;
import jdev.mentoria.lojavirtual.model.NotaItemProduto;
import jdev.mentoria.lojavirtual.repository.ContaPagarRepository;
import jdev.mentoria.lojavirtual.repository.NotaFiscalCompraRepository;

@Service
public class EmitirNotaCompraService {
	
	@Autowired
	private NotaFiscalCompraRepository repositorio;
	
	public NotaFiscalCompra emitir(NotaFiscalCompra notaFiscalCompra) {
		
		return repositorio.save(notaFiscalCompra);
		
	}
	
	public void excluirNota(Long id) throws ExceptionMentoria {
		Optional<NotaFiscalCompra> nota = repositorio.findById(id);
		
		if (nota.isEmpty()) {
			throw new ExceptionMentoria("Não foi encontrado nenhuma nota emitida.");
		}
		
		repositorio.delete(nota.get());
		
	}

}
