package jdev.mentoria.lojavirtual.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RegimeTributarioDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String optante;
	private String dataOpcao;
	private String dataExclusao;
	private String ultimaAtualizacao;
	
	

}
