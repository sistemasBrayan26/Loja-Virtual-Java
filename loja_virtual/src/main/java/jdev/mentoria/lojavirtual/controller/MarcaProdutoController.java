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
import jdev.mentoria.lojavirtual.controller.converter.MarcaProdutoModelAssembler;
import jdev.mentoria.lojavirtual.model.MarcaProduto;
import jdev.mentoria.lojavirtual.model.dto.MarcaProdutoDTO;
import jdev.mentoria.lojavirtual.repository.MarcaProdutoRepository;

@RestController
@RequestMapping("/marcas")
public class MarcaProdutoController {
	
	@Autowired
	private MarcaProdutoRepository marcaProdutoRepository;
	
	@Autowired
	private MarcaProdutoModelAssembler conversor;
	
	@PostMapping
	public ResponseEntity<MarcaProdutoDTO> salvarMarca(@RequestBody
			@Valid MarcaProduto marcaProduto) {
		
		marcaProduto = marcaProdutoRepository.save(marcaProduto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(marcaProduto.getId()).toUri();
		return ResponseEntity.created(uri).body(conversor.toModel(marcaProduto));
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) throws ExceptionMentoria {
		Optional<MarcaProduto> marcaProdutoEncontrada = marcaProdutoRepository.findById(id);
		
		if (marcaProdutoEncontrada.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		marcaProdutoRepository.delete(marcaProdutoEncontrada.get());
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/porDescricao/{descricao}")
	public ResponseEntity<List<MarcaProdutoDTO>> buscarPorDescricao(@PathVariable String descricao) {
		List<MarcaProduto> listaDeMarcas = marcaProdutoRepository.findByDescricaoContainingIgnoreCase(descricao);
		return ResponseEntity.ok(conversor.toCollectionModel(listaDeMarcas));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MarcaProdutoDTO> buscarMarcaPorCodigo(@PathVariable Long id) {
		Optional<MarcaProduto> marcaProduto = marcaProdutoRepository.findById(id);
		
		if (marcaProduto.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(conversor.toModel(marcaProduto.get()));
	}
	
	@GetMapping
	public ResponseEntity<List<MarcaProdutoDTO>> carregarTodas() {
		
		List<MarcaProduto> listaDeMarcas = marcaProdutoRepository.findAll();
		
		return ResponseEntity.ok(conversor.toCollectionModel(listaDeMarcas));
		
	}
	
	

}
