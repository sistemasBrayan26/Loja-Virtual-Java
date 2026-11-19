package jdev.mentoria.lojavirtual.controller;

import java.net.URI;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
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
		Optional<VendaCompraLojaVirtual> venda = repositorio.findByIdAndExcluidoFalse(id);
		
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
	
	@PostMapping("/cancelar/{id}")
	public ResponseEntity<String> cancelarVenda(@PathVariable Long id) throws ExceptionMentoria {
		VendaCompraLojaVirtual venda = repositorio.findById(id).
				orElseThrow(() -> new ExceptionMentoria("Venda não encontrada"));
		
		service.cancelarVenda(venda.getId());
		
		return new ResponseEntity<String>("Excluido toda a venda com sucesso", HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/voltarVenda/{id}")
	public ResponseEntity<String> estornarCancelamento(@PathVariable Long id) throws ExceptionMentoria {
		VendaCompraLojaVirtual venda = repositorio.findById(id).
				orElseThrow(() -> new ExceptionMentoria("Venda não encontrada"));
		
		service.estornarCancelamento(venda.getId());
		
		return new ResponseEntity<String>("Voltando a venda com sucesso", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/consulta/{valor}/{tipo}") 
	public ResponseEntity<List<VendaDTO>> consultaDinamica(@PathVariable String valor, 
			@PathVariable String tipo ) {
		
		List<VendaCompraLojaVirtual> vendas = new ArrayList<>();
		
		if (tipo.equalsIgnoreCase("POR_ID_PRODUTO")) {
			vendas = repositorio.findByItemVendaLojasProdutoIdAndExcluidoFalse(Long.valueOf(valor));
		} else if (tipo.equalsIgnoreCase("POR_NOME_PRODUTO")) {
			vendas = repositorio.findDistinctByItemVendaLojasProdutoNomeAndExcluidoFalse(valor);
		} else if (tipo.equalsIgnoreCase("POR_NOME_CLIENTE")) {
			vendas = repositorio.findDistinctByPessoaNomeContainingIgnoreCaseAndExcluidoFalse(valor);
		} else if (tipo.equalsIgnoreCase("POR_ENDERECO_COBRANCA")) {
			vendas = repositorio.findByEnderecoCobrancaLogradouroContainingIgnoreCaseAndExcluidoFalse(valor);
		} else if (tipo.equalsIgnoreCase("POR_ENDERECO_ENTREGA")) {
			vendas = repositorio.findByEnderecoEntregaLogradouroContainingIgnoreCaseAndExcluidoFalse(valor);
		}
		
		return ResponseEntity.ok(vendaModelAssembler.toCollectionModel(vendas));
	}
	
	@GetMapping("/porPeriodo")
	public ResponseEntity<List<VendaDTO>> consultarFaixaData(@RequestParam String dataInicial, 
			@RequestParam String dataFinal) {
		
		List<VendaCompraLojaVirtual> vendas = new ArrayList<>();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate inicial = LocalDate.parse(dataInicial, formatador);
		LocalDate fim = LocalDate.parse(dataFinal, formatador);
		vendas = repositorio.findByDataVendaBetweenAndExcluidoFalse(inicial, fim);
		
		return ResponseEntity.ok(vendaModelAssembler.toCollectionModel(vendas));
		
	}
	
	@GetMapping("/cliente/porId/{clienteId}")
	public ResponseEntity<List<VendaDTO>> consultarClientePorCodigo(@PathVariable Long clienteId) {
		return ResponseEntity.ok(vendaModelAssembler.toCollectionModel(repositorio.findByPessoaIdAndExcluidoFalse(clienteId)));
	}
	
	@GetMapping("/cliente/porNome/{nomeCliente}")
	public ResponseEntity<List<VendaDTO>> consultarClientePorNome(@PathVariable String nomeCliente) {
		return ResponseEntity.ok(vendaModelAssembler.toCollectionModel(repositorio
				.findDistinctByPessoaNomeContainingIgnoreCaseAndExcluidoFalse(nomeCliente)));
	}

}
