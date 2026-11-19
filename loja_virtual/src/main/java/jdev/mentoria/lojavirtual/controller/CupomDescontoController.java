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
import jdev.mentoria.lojavirtual.model.CupomDesconto;
import jdev.mentoria.lojavirtual.repository.CupomDescontoRepository;

@RestController
@RequestMapping("/cupons")
public class CupomDescontoController {
	
	@Autowired
	private CupomDescontoRepository repositorio;
	
	@PostMapping
	public ResponseEntity<CupomDesconto> aplicarCupom(@RequestBody @Valid CupomDesconto cupom) {
		cupom = repositorio.saveAndFlush(cupom);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cupom.getId())
				.toUri();
		return ResponseEntity.created(uri).body(cupom);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerCupom(@PathVariable Long id) throws ExceptionMentoria {
		CupomDesconto cupom = repositorio.findById(id).orElseThrow(() -> 
		new ExceptionMentoria("Não foi localizado o cupom"));
		
		repositorio.delete(cupom);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CupomDesconto> buscarPorCodigo(@PathVariable Long id) throws ExceptionMentoria {
		CupomDesconto cupom = repositorio.findById(id).orElseThrow(() -> 
		new ExceptionMentoria("Não foi localizado o cupom"));
		
		return ResponseEntity.ok(cupom);
		
	}
	
	@GetMapping
	public ResponseEntity<List<CupomDesconto>> carregarTodos() {
		return ResponseEntity.ok(repositorio.findAll());
	}

}
