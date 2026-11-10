package jdev.mentoria.lojavirtual.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jdev.mentoria.lojavirtual.ExceptionMentoria;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import jdev.mentoria.lojavirtual.service.PessoaUsuariaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaUsuariaService pessoaUsuariaService;
	
	@PostMapping
	public ResponseEntity<PessoaJuridica> salvarPJ (@RequestBody PessoaJuridica pessoaJuridica) 
			throws ExceptionMentoria {
		
		pessoaJuridica = pessoaUsuariaService.salvarPessoaJuridicaVerificandoSeExisteUsuario(pessoaJuridica);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(pessoaJuridica.getId()).toUri();
		
		return ResponseEntity.created(uri).body(pessoaJuridica);
	}

}
