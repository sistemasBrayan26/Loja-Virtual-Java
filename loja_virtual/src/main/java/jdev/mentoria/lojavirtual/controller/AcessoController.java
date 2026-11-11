package jdev.mentoria.lojavirtual.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import jdev.mentoria.lojavirtual.ExceptionMentoria;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.service.AcessoService;

@Controller
@RequestMapping("/acessos")
public class AcessoController {
	
	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@PostMapping
	public ResponseEntity<Acesso> salvar(@RequestBody @Valid Acesso acesso) throws ExceptionMentoria {
		
		try {
			acesso = acessoService.registrar(acesso);
		} catch (ExceptionMentoria e) {
			
			throw new ExceptionMentoria(e.getMessage());
		}
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(acesso.getId()).toUri();
		return ResponseEntity.created(uri).body(acesso);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) throws ExceptionMentoria {
		acessoService.deletarAcesso(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Acesso>> carregarAcessos() {
		return ResponseEntity.ok(acessoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Acesso> carregarAcessoPorId(@PathVariable Long id) throws ExceptionMentoria {
		Optional<Acesso> acessoFiltrado = acessoRepository.findById(id);
		
		if (acessoFiltrado.isEmpty()) {
			throw new ExceptionMentoria("Acesso não encontrado"); 
		}
		
		return ResponseEntity.ok(acessoFiltrado.get());
	}

}
