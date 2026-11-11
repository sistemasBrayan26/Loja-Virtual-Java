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
import jdev.mentoria.lojavirtual.model.CategoriaProduto;
import jdev.mentoria.lojavirtual.model.dto.CategoriaDTO;
import jdev.mentoria.lojavirtual.repository.CategoriaProdutoRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaProdutoController {
	
	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;
	
	@PostMapping
	public ResponseEntity<CategoriaDTO> salvarCategoria(@RequestBody
			@Valid CategoriaProduto categoria) {
		
		categoria = categoriaProdutoRepository.save(categoria);
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setId(categoria.getId());
		categoriaDTO.setDescricao(categoria.getDescricao());
		categoriaDTO.setEmpresa(categoria.getEmpresa().getId());
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(categoriaDTO);
		
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
		List<CategoriaDTO> categorias = new ArrayList<>();
		for (CategoriaProduto categoria : listaCategorias) {
			CategoriaDTO categoriaDTO = new CategoriaDTO();
			categoriaDTO.setId(categoria.getId());
			categoriaDTO.setDescricao(categoria.getDescricao());
			categoriaDTO.setEmpresa(categoria.getEmpresa().getId());
			categorias.add(categoriaDTO);
			
		}
		
		return ResponseEntity.ok(categorias);
	}
	
	

}
