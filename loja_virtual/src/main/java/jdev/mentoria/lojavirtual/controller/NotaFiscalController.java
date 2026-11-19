package jdev.mentoria.lojavirtual.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jdev.mentoria.lojavirtual.ExceptionMentoria;
import jdev.mentoria.lojavirtual.model.NotaFiscalVenda;
import jdev.mentoria.lojavirtual.repository.NotaFiscalVendaRepository;

@RestController
@RequestMapping("/notas/vendas")
public class NotaFiscalController {
	
	@Autowired
	private NotaFiscalVendaRepository repositorio;
	
	@PostMapping
	public ResponseEntity<NotaFiscalVenda> emitir(@RequestBody NotaFiscalVenda nota) {
		nota = repositorio.save(nota);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(nota.getId()).toUri();
		return ResponseEntity.created(uri).body(nota);
	}
	
	@GetMapping
	public ResponseEntity<List<NotaFiscalVenda>> buscarTodas() {
		return ResponseEntity.ok(repositorio.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NotaFiscalVenda> buscarPorId(@PathVariable Long id) throws ExceptionMentoria  {
		NotaFiscalVenda nota = repositorio.findById(id)
				.orElseThrow(() ->  new ExceptionMentoria("Nota não localizada"));
		return ResponseEntity.ok(nota);
	}
	
	@GetMapping("/obterNFDaVenda/{idVenda}")
	public ResponseEntity<NotaFiscalVenda> obterNotaFiscalAPartirDaVenda(@PathVariable Long idVenda) throws ExceptionMentoria {
		NotaFiscalVenda nota = repositorio.findByVendaCompraLojaVirtualId(idVenda)
				.orElseThrow(() -> new ExceptionMentoria("Nota fiscal nao localizada"));
		return ResponseEntity.ok(nota);
	}

}
