package jdev.mentoria.lojavirtual.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class FormaPagamentoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String descricao;
	
	private Long empresaId;

}
