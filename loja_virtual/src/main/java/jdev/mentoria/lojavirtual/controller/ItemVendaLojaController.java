package jdev.mentoria.lojavirtual.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import jdev.mentoria.lojavirtual.model.ItemVendaLoja;
import jdev.mentoria.lojavirtual.repository.ItemVendaLojaRepository;

@RestController
@RequestMapping("/vendas/itens")
public class ItemVendaLojaController {
	
	@Autowired
	private ItemVendaLojaRepository repositorio;
	
	@PostMapping("/adicionar")
	public ResponseEntity<ItemVendaLoja> adicionar(@RequestBody @Valid ItemVendaLoja item) {
		item = repositorio.saveAndFlush(item);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(item.getId()).toUri();
		return ResponseEntity.created(uri).body(item);
	}

}
