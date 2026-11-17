package jdev.mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class ItemVendaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private BigDecimal quantidade;
	private Long produtoId;

}
