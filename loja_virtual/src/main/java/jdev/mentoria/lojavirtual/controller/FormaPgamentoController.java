package jdev.mentoria.lojavirtual.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import jdev.mentoria.lojavirtual.controller.converter.FormaPagamentoModelAssembler;
import jdev.mentoria.lojavirtual.model.FormaPagamento;
import jdev.mentoria.lojavirtual.model.dto.FormaPagamentoDTO;
import jdev.mentoria.lojavirtual.repository.FormaPagamentoRepository;

@RestController
@RequestMapping("/formas")
public class FormaPgamentoController {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private FormaPagamentoModelAssembler assembler;
	
	@PostMapping
	public ResponseEntity<FormaPagamentoDTO> adicionarFormaPagamento(@RequestBody @Valid 
			FormaPagamento forma) {
		
		forma = formaPagamentoRepository.saveAndFlush(forma);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(forma.getId()).toUri();
		return ResponseEntity.created(uri).body(assembler.toModel(forma));
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirForma(@PathVariable Long id) {
		Optional<FormaPagamento> forma = formaPagamentoRepository.findById(id);
		
		if (forma.isEmpty()) {
			return new ResponseEntity<String>("Não foi encontrada nenhuma forma", HttpStatus.NOT_FOUND);
		}
		
		formaPagamentoRepository.delete(forma.get());
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> buscarTodasFormas() {
		List<FormaPagamento> formas = formaPagamentoRepository.findAll();
		return ResponseEntity.ok(assembler.toCollectionModel(formas));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FormaPagamentoDTO> buscarPorId(@PathVariable Long id) throws ExceptionMentoria {
		Optional<FormaPagamento> forma = formaPagamentoRepository.findById(id);
		
		if (forma.isEmpty()) {
			throw new ExceptionMentoria("Forma de pagamento nao encontrada");
		}
		
		return ResponseEntity.ok(assembler.toModel(forma.get()));
	}

}
