package jdev.mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jdev.mentoria.lojavirtual.model.enums.StatusVendaLojaVirtual;
import lombok.Data;

@Data
public class VendaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long pessoaId;
	private Long enderecoEntregaId;
	private Long enderecoCobrancaId;
	private BigDecimal valorTotal;
	private BigDecimal valorDesconto;
	private Long formaPagamentoId;
	private Long notaFiscalVendaId;
	private Long cupomDescontoId;
	private BigDecimal valorFrete;
	private Integer diasEntrega;
	private LocalDate dataVenda;
	private LocalDate dataEntrega;
	private StatusVendaLojaVirtual status;
	private Long empresaId;
	private List<ItemVendaDTO> itemVendaLojas;

}
