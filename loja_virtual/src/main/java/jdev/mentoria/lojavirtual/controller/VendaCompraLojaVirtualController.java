package jdev.mentoria.lojavirtual.controller;

import java.net.URI;
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
import jdev.mentoria.lojavirtual.ExceptionMentoria;
import jdev.mentoria.lojavirtual.controller.converter.VendaModelAssembler;
import jdev.mentoria.lojavirtual.model.VendaCompraLojaVirtual;
import jdev.mentoria.lojavirtual.model.dto.VendaDTO;
import jdev.mentoria.lojavirtual.repository.VendaCompraLojaVirtualRepository;
import jdev.mentoria.lojavirtual.service.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaCompraLojaVirtualController {
	
	@Autowired
	private VendaService service;
	
	@Autowired
	private VendaCompraLojaVirtualRepository repositorio;
	
	@Autowired
	private VendaModelAssembler vendaModelAssembler;
	
	@PostMapping
	public ResponseEntity<VendaDTO> emitirVenda(@RequestBody @Valid VendaCompraLojaVirtual venda) {
		venda = service.salvar(venda);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(venda.getId()).toUri();
		
		return ResponseEntity.created(uri).body(vendaModelAssembler.toModel(venda));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VendaDTO> consultarVendaPorCodigo(@PathVariable Long id) throws ExceptionMentoria {
		Optional<VendaCompraLojaVirtual> venda = repositorio.findById(id);
		
		if (venda.isEmpty()) {
			throw new ExceptionMentoria("Não existe venda com o ID: " + id);
		}
		
		return ResponseEntity.ok(vendaModelAssembler.toModel(venda.get()));
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<String> deletarVendaTotalmente(@PathVariable Long id) throws ExceptionMentoria {
		VendaCompraLojaVirtual venda = repositorio.findById(id).
				orElseThrow(() -> new ExceptionMentoria("Venda não encontrada"));
		repositorio.exclusaoTotalVendaBanco(venda.getId());
		
		return new ResponseEntity<String>("Excluido toda a venda com sucesso", HttpStatus.NO_CONTENT);
	}

}
