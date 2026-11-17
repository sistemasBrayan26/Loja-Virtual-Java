package jdev.mentoria.lojavirtual.controller.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jdev.mentoria.lojavirtual.model.CategoriaProduto;
import jdev.mentoria.lojavirtual.model.FormaPagamento;
import jdev.mentoria.lojavirtual.model.MarcaProduto;
import jdev.mentoria.lojavirtual.model.VendaCompraLojaVirtual;
import jdev.mentoria.lojavirtual.model.dto.CategoriaDTO;
import jdev.mentoria.lojavirtual.model.dto.FormaPagamentoDTO;
import jdev.mentoria.lojavirtual.model.dto.MarcaProdutoDTO;
import jdev.mentoria.lojavirtual.model.dto.VendaDTO;

@Configuration
public class VendaModelAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public VendaDTO toModel(VendaCompraLojaVirtual venda) {
		return mapper.map(venda, VendaDTO.class);
	}
	
	public List<VendaDTO> toCollectionModel(List<VendaCompraLojaVirtual> vendas) {
		return vendas.stream().map(venda -> toModel(venda)).collect(Collectors.toList());
	}

}
