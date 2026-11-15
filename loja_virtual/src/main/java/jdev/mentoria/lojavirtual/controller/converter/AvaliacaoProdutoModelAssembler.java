package jdev.mentoria.lojavirtual.controller.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jdev.mentoria.lojavirtual.model.AvaliacaoProduto;
import jdev.mentoria.lojavirtual.model.CategoriaProduto;
import jdev.mentoria.lojavirtual.model.MarcaProduto;
import jdev.mentoria.lojavirtual.model.dto.AvaliacaoProdutoDTO;
import jdev.mentoria.lojavirtual.model.dto.CategoriaDTO;
import jdev.mentoria.lojavirtual.model.dto.MarcaProdutoDTO;

@Configuration
public class AvaliacaoProdutoModelAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public AvaliacaoProdutoDTO toModel(AvaliacaoProduto avaliacaoProduto) {
		return mapper.map(avaliacaoProduto, AvaliacaoProdutoDTO.class);
	}
	
	public List<AvaliacaoProdutoDTO> toCollectionModel(List<AvaliacaoProduto> avaliacoes) {
		return avaliacoes.stream().map(avaliacao -> toModel(avaliacao)).collect(Collectors.toList());
	}

}
