package jdev.mentoria.lojavirtual.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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
import jdev.mentoria.lojavirtual.model.dto.ConsultaCnpjDTO;
import jdev.mentoria.lojavirtual.repository.PessoaFisicaRepository;
import jdev.mentoria.lojavirtual.repository.PessoaJuridicaRepository;
import jdev.mentoria.lojavirtual.service.PessoaUsuariaService;
import jdev.mentoria.lojavirtual.service.TabelaEndPointService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaUsuariaService pessoaUsuariaService;
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	
	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;
	
	@Autowired
	private TabelaEndPointService tabelaEndPointService;
	
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
	
	@GetMapping("/consultaPJNome/{nome}")
	public ResponseEntity<List<PessoaJuridica>> consultaPJPorNome(@PathVariable String nome) {
		return ResponseEntity.ok(pessoaJuridicaRepository.findByNomeContainingIgnoreCase(nome));
	}
	
	@GetMapping("/consultaPFNome/{nome}")
	public ResponseEntity<List<PessoaFisica>> consultaPFPorNome(@PathVariable String nome) {
		tabelaEndPointService.atualizarEndPointPessoaFisica();
		return ResponseEntity.ok(pessoaFisicaRepository.findByNomeContainingIgnoreCase(nome));
	}
	
	@GetMapping("/consultaPJCnpj/{nome}")
	public ResponseEntity<List<PessoaJuridica>> consultaPorCnpj(@PathVariable String cnpj) {
		return ResponseEntity.ok(pessoaJuridicaRepository.findByCnpjContainingIgnoreCase(cnpj));
	}
	
	@GetMapping("/consultaPFCpf/{nome}")
	public ResponseEntity<List<PessoaFisica>> consultaPFPorCpf(@PathVariable String cpf) {
		return ResponseEntity.ok(pessoaFisicaRepository.findByCpfContainingIgnoreCase(cpf));
	}
	
	@GetMapping("/consultaCnpj/{cnpj}")
	public ResponseEntity<ConsultaCnpjDTO> consultarPorCnpjReceita(@PathVariable String cnpj) {
		ConsultaCnpjDTO consultaCnpjDTO = pessoaUsuariaService.consultarCnpj(cnpj);
		return new ResponseEntity<ConsultaCnpjDTO>(consultaCnpjDTO, HttpStatus.OK);
	}

}
