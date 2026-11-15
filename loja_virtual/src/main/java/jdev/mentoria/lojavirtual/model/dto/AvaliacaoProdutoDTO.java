package jdev.mentoria.lojavirtual.model.dto;

import lombok.Data;

@Data
public class AvaliacaoProdutoDTO {
	
	private Long id;
	private Integer nota;
	private String descricao;
	private Long pessoaId;
	private Long empresaId;
	private Long produtoId;

}
