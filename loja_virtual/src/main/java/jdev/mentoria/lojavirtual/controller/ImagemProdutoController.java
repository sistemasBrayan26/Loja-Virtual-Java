package jdev.mentoria.lojavirtual.controller;

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

import jakarta.validation.Valid;
import jdev.mentoria.lojavirtual.controller.converter.ImagemProdutoModelAssembler;
import jdev.mentoria.lojavirtual.model.ImagemProduto;
import jdev.mentoria.lojavirtual.model.dto.ImagemProdutoDTO;
import jdev.mentoria.lojavirtual.repository.ImagemProdutoRepository;

@RestController
@RequestMapping("/produtos/imagens")
public class ImagemProdutoController {
	
	@Autowired
	private ImagemProdutoRepository repositorio;
	
	@Autowired
	private ImagemProdutoModelAssembler assembler;
	
	@PostMapping
	public ResponseEntity<ImagemProdutoDTO> salvarImagem(@RequestBody @Valid ImagemProduto imagem) {
		imagem = repositorio.saveAndFlush(imagem);
		
		return new ResponseEntity<ImagemProdutoDTO>(assembler.toModel(imagem), HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{produtoId}")
	public ResponseEntity<List<ImagemProduto>> carregandoImagemPorProduto(@PathVariable Long produtoId) {
		
		List<ImagemProduto> imagensProdutos = repositorio.findByProdutoId(produtoId);
		
		return new ResponseEntity<List<ImagemProduto>>(imagensProdutos, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirImagem(@PathVariable Long id) {
		
		Optional<ImagemProduto> imagem = repositorio.findById(id);
		
		if (imagem.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		repositorio.delete(imagem.get());
		
		return ResponseEntity.noContent().build();
		
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@RequestBody ImagemProduto imagem) {
		
		repositorio.delete(imagem);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@DeleteMapping("/deletarPorProduto/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long produtoId) {
		
		repositorio.deleteByProdutoId(produtoId);
		
		return ResponseEntity.noContent().build();
		
	}
	
	

}
