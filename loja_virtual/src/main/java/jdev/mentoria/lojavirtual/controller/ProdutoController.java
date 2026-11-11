package jdev.mentoria.lojavirtual.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import jdev.mentoria.lojavirtual.model.Produto;
import jdev.mentoria.lojavirtual.repository.ProdutoRepository;
import jdev.mentoria.lojavirtual.service.CadastroProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	
	@PostMapping
	public ResponseEntity<Produto> salvar(@RequestBody @Valid Produto produto) {
		
		produto = cadastroProdutoService.registrar(produto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(produto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(produto);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarProduto(@PathVariable Long id) {
		Optional<Produto> produtoFiltrado = produtoRepository.findById(id);
		
		if (produtoFiltrado.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		produtoRepository.delete(produtoFiltrado.get());
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/porCodigo/{id}")
	public ResponseEntity<Produto> buscarPorCodigo(@PathVariable Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if (produto.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(produto.get());
	}
	
	@GetMapping("/porNome/{nome}")
	public ResponseEntity<List<Produto>> buscarPorNome(@PathVariable String nome) {
		List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
		return ResponseEntity.ok(produtos);
	}

}
