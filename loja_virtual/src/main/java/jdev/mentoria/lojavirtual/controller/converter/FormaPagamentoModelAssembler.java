package jdev.mentoria.lojavirtual.controller.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jdev.mentoria.lojavirtual.model.CategoriaProduto;
import jdev.mentoria.lojavirtual.model.FormaPagamento;
import jdev.mentoria.lojavirtual.model.MarcaProduto;
import jdev.mentoria.lojavirtual.model.dto.CategoriaDTO;
import jdev.mentoria.lojavirtual.model.dto.FormaPagamentoDTO;
import jdev.mentoria.lojavirtual.model.dto.MarcaProdutoDTO;

@Configuration
public class FormaPagamentoModelAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public FormaPagamentoDTO toModel(FormaPagamento forma) {
		return mapper.map(forma, FormaPagamentoDTO.class);
	}
	
	public List<FormaPagamentoDTO> toCollectionModel(List<FormaPagamento> formas) {
		return formas.stream().map(forma -> toModel(forma)).collect(Collectors.toList());
	}

}
