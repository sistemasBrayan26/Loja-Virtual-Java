package jdev.mentoria.lojavirtual.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.ExceptionMentoria;
import jdev.mentoria.lojavirtual.model.NotaItemProduto;
import jdev.mentoria.lojavirtual.repository.NotaItemProdutoRepository;

@Service
public class OperacaoItemCompraService {
	
	@Autowired
	private NotaItemProdutoRepository repositorio;
	
	@Transactional
	public NotaItemProduto adicionarItem(NotaItemProduto item) {
		
		Optional<NotaItemProduto> itemJaAdicionado = repositorio.findByProdutoIdAndNotaFiscalCompraId
				(item.getProduto().getId(), item.getNotaFiscalCompra().getId());
		
		if (itemJaAdicionado.isPresent()) {
			NotaItemProduto itemExistente = itemJaAdicionado.get();
			itemExistente.setQuantidade(itemExistente.getQuantidade().add(item.getQuantidade()));
			return repositorio.save(itemExistente);
		}
		
		return repositorio.save(item);
	}
	
	@Transactional
	public void removerItem(Long id) throws ExceptionMentoria {
		Optional<NotaItemProduto> item = repositorio.findById(id);
		
		if (item.isEmpty()) {
			throw new ExceptionMentoria("Item não localizado.");
		}
		
		repositorio.delete(item.get());
	}

}
