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
import jdev.mentoria.lojavirtual.model.ContaPagar;
import jdev.mentoria.lojavirtual.repository.ContaPagarRepository;
import jdev.mentoria.lojavirtual.service.OperacaoPagarService;

@RestController
@RequestMapping("/contas/pagar")
public class ContaPagarController {
	
	@Autowired
	private OperacaoPagarService operacaoPagar;
	
	@Autowired
	private ContaPagarRepository contaPagarRepository;
	
	@PostMapping
	public ResponseEntity<ContaPagar> emitir(@RequestBody @Valid ContaPagar conta) {
		conta = operacaoPagar.emitir(conta);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(conta.getId()).toUri();
		return ResponseEntity.created(uri).body(conta);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> cancelar(@PathVariable Long id) throws ExceptionMentoria {
		operacaoPagar.cancelar(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/porDescricao/{descricao}")
	public ResponseEntity<List<ContaPagar>> buscarPorDescricao(@PathVariable String descricao) {
		List<ContaPagar> contas = contaPagarRepository.findByDescricaoContainingIgnoreCase(descricao);
		return ResponseEntity.ok(contas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ContaPagar> buscarPorId(@PathVariable Long id) {
		Optional<ContaPagar> conta = contaPagarRepository.findById(id);
		
		if (conta.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(conta.get());
	}

}
