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
import jdev.mentoria.lojavirtual.model.VendaCompraLojaVirtual;
import jdev.mentoria.lojavirtual.repository.VendaCompraLojaVirtualRepository;
import jdev.mentoria.lojavirtual.service.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaCompraLojaVirtualController {
	
	@Autowired
	private VendaService service;
	
	@PostMapping
	public ResponseEntity<VendaCompraLojaVirtual> emitirVenda(@RequestBody @Valid VendaCompraLojaVirtual venda) {
		venda = service.salvar(venda);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(venda.getId()).toUri();
		
		return ResponseEntity.created(uri).body(venda);
	}

}
