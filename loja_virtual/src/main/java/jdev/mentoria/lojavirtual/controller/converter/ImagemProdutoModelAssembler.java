package jdev.mentoria.lojavirtual.controller.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jdev.mentoria.lojavirtual.model.ImagemProduto;
import jdev.mentoria.lojavirtual.model.MarcaProduto;
import jdev.mentoria.lojavirtual.model.dto.ImagemProdutoDTO;
import jdev.mentoria.lojavirtual.model.dto.MarcaProdutoDTO;

@Configuration
public class ImagemProdutoModelAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public ImagemProdutoDTO toModel(ImagemProduto imagemProduto) {
		return mapper.map(imagemProduto, ImagemProdutoDTO.class);
	}
	
	public List<ImagemProdutoDTO> toCollectionModel(List<ImagemProduto> imagens) {
		return imagens.stream().map(imagem -> toModel(imagem)).collect(Collectors.toList());
	}

}
