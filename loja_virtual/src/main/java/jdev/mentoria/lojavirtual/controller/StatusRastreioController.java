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
import jdev.mentoria.lojavirtual.model.StatusRastreio;
import jdev.mentoria.lojavirtual.repository.StatusRastreioRepository;

@RestController
@RequestMapping("/rastreios")
public class StatusRastreioController {
	
	@Autowired
	private StatusRastreioRepository repositorio;
	
	@GetMapping("/venda/{idVenda}")
	public ResponseEntity<List<StatusRastreio>> rastrearPorVenda(@PathVariable Long vendaId) {
		return ResponseEntity.ok(repositorio.findByVendaCompraLojaVirtualId(vendaId));
	}
	
	@GetMapping
	public ResponseEntity<List<StatusRastreio>> carregar() {
		return ResponseEntity.ok(repositorio.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StatusRastreio> buscarPorId(@PathVariable Long id) throws ExceptionMentoria {
		StatusRastreio statusRastreio = repositorio.findById(id).orElseThrow(() -> new ExceptionMentoria("Não localizado o rastreio"));
		return ResponseEntity.ok(statusRastreio);
	}
	
	@PostMapping
	public ResponseEntity<StatusRastreio> registrar(@RequestBody @Valid StatusRastreio statusRastreio) {
		statusRastreio = repositorio.saveAndFlush(statusRastreio);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(statusRastreio.getId()).toUri();
		return ResponseEntity.created(uri).body(statusRastreio);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) throws ExceptionMentoria {
		StatusRastreio statusRastreio = repositorio.findById(id).orElseThrow(() -> new ExceptionMentoria("Não localizado o rastreio"));
		repositorio.delete(statusRastreio);
		return ResponseEntity.noContent().build();
	}

}
