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
import jdev.mentoria.lojavirtual.model.NotaFiscalCompra;
import jdev.mentoria.lojavirtual.repository.NotaFiscalCompraRepository;
import jdev.mentoria.lojavirtual.service.EmitirNotaCompraService;

@RestController
@RequestMapping("/notas/compras")
public class NotaFiscalCompraController {
	
	@Autowired
	private EmitirNotaCompraService emissaoService;
	
	@Autowired
	private NotaFiscalCompraRepository repositorio;
	
	@PostMapping
	public ResponseEntity<NotaFiscalCompra> emitir(@RequestBody @Valid NotaFiscalCompra nota) {
		nota = emissaoService.emitir(nota);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(nota.getId()).toUri();
		return ResponseEntity.created(uri).body(nota);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> cancelar(@PathVariable Long id) throws ExceptionMentoria {
		emissaoService.excluirNota(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/porDescricao/{descricao}")
	public ResponseEntity<List<NotaFiscalCompra>> buscarPorDescricao(@PathVariable String observacao) {
		List<NotaFiscalCompra> notas = repositorio.findByObservacaoContainingIgnoreCase(observacao);
		return ResponseEntity.ok(notas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NotaFiscalCompra> buscarPorId(@PathVariable Long id) {
		Optional<NotaFiscalCompra> nota = repositorio.findById(id);
		
		if (nota.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(nota.get());
	}

}
