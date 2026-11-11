package jdev.mentoria.lojavirtual.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class QsaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String qual;
	private String paisOrigem;
	private String nomeRepLegal;
	private String qualRepLegal;

}
