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
import jdev.mentoria.lojavirtual.controller.converter.AvaliacaoProdutoModelAssembler;
import jdev.mentoria.lojavirtual.model.AvaliacaoProduto;
import jdev.mentoria.lojavirtual.model.dto.AvaliacaoProdutoDTO;
import jdev.mentoria.lojavirtual.repository.AvaliacaoProdutoRepository;

@RestController
@RequestMapping("/produto/avaliacao")
public class AvaliacaoProdutoController {
	
	@Autowired
	private AvaliacaoProdutoRepository repositorio;
	
	@Autowired
	private AvaliacaoProdutoModelAssembler modelAssembler;
	
	@PostMapping
	public ResponseEntity<AvaliacaoProdutoDTO> avaliar(@RequestBody @Valid AvaliacaoProduto avaliacao) {
		
		avaliacao = repositorio.saveAndFlush(avaliacao);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(avaliacao.getId()).toUri();
		return ResponseEntity.created(uri).body(modelAssembler.toModel(avaliacao));
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarAvaliacao(@PathVariable Long id) {
		
		Optional<AvaliacaoProduto> avaliacao = repositorio.findById(id);
		
		if (avaliacao.isEmpty()) {
			return new ResponseEntity<String>("Avaliação não encontrada", HttpStatus.NOT_FOUND);
		}
		
		repositorio.delete(avaliacao.get());
		
		return new ResponseEntity<String>("Avaliação removida com sucesso", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/porProduto/{produtoId}")
	public ResponseEntity<List<AvaliacaoProdutoDTO>> avaliacaoPorProduto(@PathVariable Long produtoId) {
		List<AvaliacaoProduto> avaliacoesPorProduto = repositorio.findByProdutoId(produtoId);
		
		return ResponseEntity.ok(modelAssembler.toCollectionModel(avaliacoesPorProduto));
	}
	
	@GetMapping("/porPessoa/{pessoaId}")
	public ResponseEntity<List<AvaliacaoProdutoDTO>> avaliacaoPorPessoa(@PathVariable Long pessoaId) {
		List<AvaliacaoProduto> avaliacoesPorProduto = repositorio.findByPessoaId(pessoaId);
		
		return ResponseEntity.ok(modelAssembler.toCollectionModel(avaliacoesPorProduto));
	}
	
	@GetMapping("/comProdutoPessoa/{produtoId}/{pessoaId}")
	public ResponseEntity<List<AvaliacaoProdutoDTO>> avaliacaoPorProdutoPessoa(@PathVariable Long produtoId, 
			@PathVariable Long pessoaId) {
		List<AvaliacaoProduto> avaliacoesPorProduto = repositorio.findByProdutoIdAndPessoaId(produtoId, pessoaId);
		
		return ResponseEntity.ok(modelAssembler.toCollectionModel(avaliacoesPorProduto));
	}

}
