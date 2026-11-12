package jdev.mentoria.lojavirtual.controller.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jdev.mentoria.lojavirtual.model.CategoriaProduto;
import jdev.mentoria.lojavirtual.model.MarcaProduto;
import jdev.mentoria.lojavirtual.model.dto.CategoriaDTO;
import jdev.mentoria.lojavirtual.model.dto.MarcaProdutoDTO;

@Configuration
public class MarcaProdutoModelAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public MarcaProdutoDTO toModel(MarcaProduto marcaProduto) {
		return mapper.map(marcaProduto, MarcaProdutoDTO.class);
	}
	
	public List<MarcaProdutoDTO> toCollectionModel(List<MarcaProduto> marcas) {
		return marcas.stream().map(categoria -> toModel(categoria)).collect(Collectors.toList());
	}

}
