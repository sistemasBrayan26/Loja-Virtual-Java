package jdev.mentoria.lojavirtual.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import jdev.mentoria.lojavirtual.ExceptionMentoria;
import jdev.mentoria.lojavirtual.model.PessoaFisica;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import jdev.mentoria.lojavirtual.model.dto.CepDTO;
import jdev.mentoria.lojavirtual.service.PessoaUsuariaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaUsuariaService pessoaUsuariaService;
	
	@PostMapping("/salvarPJ")
	public ResponseEntity<PessoaJuridica> salvarPJ (@RequestBody @Valid PessoaJuridica pessoaJuridica) 
			throws ExceptionMentoria {
		
		pessoaJuridica = pessoaUsuariaService.salvarPessoaJuridicaVerificandoSeExisteUsuario(pessoaJuridica);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(pessoaJuridica.getId()).toUri();
		
		return ResponseEntity.created(uri).body(pessoaJuridica);
	}
	
	@PostMapping("/salvarPF")
	public ResponseEntity<PessoaFisica> salvarPF (@RequestBody @Valid PessoaFisica pessoaFisica) 
			throws ExceptionMentoria {
		
		pessoaFisica = pessoaUsuariaService.salvarPessoaFisicaVerificandoSeExisteUsuario(pessoaFisica);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(pessoaFisica.getId()).toUri();
		
		return ResponseEntity.created(uri).body(pessoaFisica);
	}
	
	@GetMapping("/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultarCep(@PathVariable String cep) {
		CepDTO cepDTO = pessoaUsuariaService.consultaCep(cep);
		return new ResponseEntity<CepDTO>(cepDTO, HttpStatus.OK);
	}

}
