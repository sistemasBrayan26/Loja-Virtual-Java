package jdev.mentoria.lojavirtual.controller;

import java.net.URI;
import java.util.ArrayList;
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
import jdev.mentoria.lojavirtual.controller.converter.CategoriaProdutoModelAssembler;
import jdev.mentoria.lojavirtual.model.CategoriaProduto;
import jdev.mentoria.lojavirtual.model.dto.CategoriaDTO;
import jdev.mentoria.lojavirtual.repository.CategoriaProdutoRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaProdutoController {
	
	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;
	
	@Autowired
	private CategoriaProdutoModelAssembler conversor;
	
	@PostMapping
	public ResponseEntity<CategoriaDTO> salvarCategoria(@RequestBody
			@Valid CategoriaProduto categoria) {
		
		categoria = categoriaProdutoRepository.save(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(conversor.toModel(categoria));
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) throws ExceptionMentoria {
		Optional<CategoriaProduto> categoriaEncontrada = categoriaProdutoRepository.findById(id);
		
		if (categoriaEncontrada.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		categoriaProdutoRepository.delete(categoriaEncontrada.get());
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/porDescricao/{descricao}")
	public ResponseEntity<List<CategoriaDTO>> buscarPorDescricao(@PathVariable String descricao) {
		List<CategoriaProduto> listaCategorias = categoriaProdutoRepository.findByDescricaoContainingIgnoreCase(descricao);
		return ResponseEntity.ok(conversor.toCollectionModel(listaCategorias));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTO> buscarCategoriaPorCodigo(@PathVariable Long id) {
		Optional<CategoriaProduto> categoria = categoriaProdutoRepository.findById(id);
		
		if (categoria.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(conversor.toModel(categoria.get()));
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> carregarTodas() {
		
		List<CategoriaProduto> listaCategorias = categoriaProdutoRepository.findAll();
		
		return ResponseEntity.ok(conversor.toCollectionModel(listaCategorias));
		
	}
	
	

}
