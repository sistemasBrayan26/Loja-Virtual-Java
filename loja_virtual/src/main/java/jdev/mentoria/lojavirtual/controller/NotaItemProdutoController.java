package jdev.mentoria.lojavirtual.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import jdev.mentoria.lojavirtual.ExceptionMentoria;
import jdev.mentoria.lojavirtual.model.NotaItemProduto;
import jdev.mentoria.lojavirtual.repository.NotaItemProdutoRepository;
import jdev.mentoria.lojavirtual.service.OperacaoItemCompraService;

@RestController
@RequestMapping("/notas/itens")
public class NotaItemProdutoController {
	
	@Autowired
	private NotaItemProdutoRepository repositorio;
	
	@Autowired
	private OperacaoItemCompraService itemService;
	
	@PostMapping
	public ResponseEntity<NotaItemProduto> adicionarItem(@RequestBody @Valid NotaItemProduto item) {
		
		item = itemService.adicionarItem(item);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(item.getId()).toUri();
		return ResponseEntity.created(uri).body(item);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerItem(@PathVariable Long id) throws ExceptionMentoria {
		itemService.removerItem(id);
		
		return ResponseEntity.noContent().build();
	}
	

}
