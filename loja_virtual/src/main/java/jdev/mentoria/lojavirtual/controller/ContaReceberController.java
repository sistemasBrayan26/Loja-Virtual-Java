package jdev.mentoria.lojavirtual.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import jdev.mentoria.lojavirtual.model.ContaReceber;
import jdev.mentoria.lojavirtual.repository.ContaReceberRepository;

@RestController
@RequestMapping("/contas/receber")
public class ContaReceberController {
	
	@Autowired
	private ContaReceberRepository repositorio;
	
	@PostMapping
	public ResponseEntity<ContaReceber> emitir(@RequestBody @Valid ContaReceber conta) {
		conta = repositorio.saveAndFlush(conta);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(conta.getId()).toUri();
		return ResponseEntity.created(uri).body(conta);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> cancelar(@PathVariable Long id) throws ExceptionMentoria {
		ContaReceber conta = repositorio.findById(id).orElseThrow(() -> new ExceptionMentoria("Conta nao localizada"));
		repositorio.delete(conta);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/porDescricao/{descricao}")
	public ResponseEntity<List<ContaReceber>> buscarPorDescricao(@PathVariable String descricao) {
		List<ContaReceber> contas = repositorio.findByDescricaoContainingIgnoreCase(descricao);
		return ResponseEntity.ok(contas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ContaReceber> buscarPorId(@PathVariable Long id) {
		Optional<ContaReceber> conta = repositorio.findById(id);
		
		if (conta.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(conta.get());
	}

}
