package jdev.mentoria.lojavirtual.controller.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jdev.mentoria.lojavirtual.model.CategoriaProduto;
import jdev.mentoria.lojavirtual.model.dto.CategoriaDTO;

@Configuration
public class CategoriaProdutoModelAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public CategoriaDTO toModel(CategoriaProduto categoriaProduto) {
		return mapper.map(categoriaProduto, CategoriaDTO.class);
	}
	
	public List<CategoriaDTO> toCollectionModel(List<CategoriaProduto> categorias) {
		return categorias.stream().map(categoria -> toModel(categoria)).collect(Collectors.toList());
	}

}
