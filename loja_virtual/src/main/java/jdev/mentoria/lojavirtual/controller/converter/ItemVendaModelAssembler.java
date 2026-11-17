package jdev.mentoria.lojavirtual.controller.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jdev.mentoria.lojavirtual.model.CategoriaProduto;
import jdev.mentoria.lojavirtual.model.FormaPagamento;
import jdev.mentoria.lojavirtual.model.ItemVendaLoja;
import jdev.mentoria.lojavirtual.model.MarcaProduto;
import jdev.mentoria.lojavirtual.model.VendaCompraLojaVirtual;
import jdev.mentoria.lojavirtual.model.dto.CategoriaDTO;
import jdev.mentoria.lojavirtual.model.dto.FormaPagamentoDTO;
import jdev.mentoria.lojavirtual.model.dto.ItemVendaDTO;
import jdev.mentoria.lojavirtual.model.dto.MarcaProdutoDTO;
import jdev.mentoria.lojavirtual.model.dto.VendaDTO;

@Configuration
public class ItemVendaModelAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public ItemVendaDTO toModel(ItemVendaLoja item) {
		return mapper.map(item, ItemVendaDTO.class);
	}
	
	public List<ItemVendaDTO> toCollectionModel(List<ItemVendaLoja> itens) {
		return itens.stream().map(item -> toModel(item)).collect(Collectors.toList());
	}

}
