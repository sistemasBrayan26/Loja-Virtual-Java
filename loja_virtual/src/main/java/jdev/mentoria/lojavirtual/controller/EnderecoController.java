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
import jdev.mentoria.lojavirtual.model.Endereco;
import jdev.mentoria.lojavirtual.repository.EnderecoRepository;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@PostMapping
	public ResponseEntity<Endereco> salvar(@RequestBody @Valid Endereco endereco) {
		endereco = enderecoRepository.saveAndFlush(endereco);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(endereco.getId()).toUri();
		return ResponseEntity.created(uri).body(endereco);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) throws ExceptionMentoria {
		Endereco endereco = enderecoRepository.findById(id)
				.orElseThrow(() -> new ExceptionMentoria("Endereço não encontrado"));
		enderecoRepository.delete(endereco);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Endereco> buscarPorId(@PathVariable Long id) throws ExceptionMentoria {
		Endereco endereco = enderecoRepository.findById(id)
				.orElseThrow(() -> new ExceptionMentoria("Endereço não encontrado"));
		return ResponseEntity.ok(endereco);
	}
	
	@GetMapping
	public ResponseEntity<List<Endereco>> buscar() {
		return ResponseEntity.ok(enderecoRepository.findAll());
	}

}
