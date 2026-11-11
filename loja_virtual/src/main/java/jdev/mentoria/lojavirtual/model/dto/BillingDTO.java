package jdev.mentoria.lojavirtual.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class BillingDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String free;
	private String dataBase;

}
