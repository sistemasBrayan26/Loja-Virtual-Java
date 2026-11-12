package jdev.mentoria.lojavirtual.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MarcaProdutoDTO {
	
	private Long id;
	private String descricao;
	private Long empresaId;

}
