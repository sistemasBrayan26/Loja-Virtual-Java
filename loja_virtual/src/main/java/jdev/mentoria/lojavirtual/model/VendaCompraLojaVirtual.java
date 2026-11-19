package jdev.mentoria.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jdev.mentoria.lojavirtual.model.enums.StatusVendaLojaVirtual;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Data
public class VendaCompraLojaVirtual implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "A pessoa é obrigatória")
	@ManyToOne
	@JoinColumn(name = "pessoa_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private PessoaFisica pessoa;
	
	@NotNull(message = "Endereço para entrega é obrigatório")
	@ManyToOne
	@JoinColumn(name = "endereco_entrega_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "endereco_entrega_fk"))
	private Endereco enderecoEntrega;
	
	@NotNull(message = "Endereço de cobrança é obrigatório")
	@ManyToOne
	@JoinColumn(name = "endereco_cobranca_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "endereco_cobranca_fk"))
	private Endereco enderecoCobranca;
	

	@NotNull(message = "Valor total nao pode ser nulo")
	@Positive(message = "O valor total não pode ser negativa")
	@DecimalMin(value = "0.00")
	@Column(nullable = false)
	private BigDecimal valorTotal;
	
	private BigDecimal valorDesconto;
	
	@NotNull(message = "Forma de pagamento é obrigatória")
	@ManyToOne
	@JoinColumn(name = "forma_pagamento_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "forma_pagamento_fk"))
	private FormaPagamento formaPagamento;
	
	@OneToOne
	@JoinColumn(name = "nota_fiscal_venda_id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "nota_fiscal_venda_fk"))
	private NotaFiscalVenda notaFiscalVenda;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "cupom_desc_fk"))
	private CupomDesconto cupomDesconto;
	
	@NotNull(message = "Valor de frete é obrigatorio")
	@Positive(message = "O valor de frente tem que ser positivo")
	@Column(nullable = false)
	private BigDecimal valorFrete;
	
	@NotNull(message = "dias de entrega deve ser informado")
	@Column(nullable = false)
	private Integer diasEntrega;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "A data da venda deve ser informada")
	@Column(nullable = false)
	private LocalDate dataVenda;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(nullable = false)
	private LocalDate dataEntrega;
	
	@NotNull(message = "A empresa deve ser informada")
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;
	
	@NotNull(message = "Status é obrigatório")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusVendaLojaVirtual status;
	
	private Boolean excluido = Boolean.FALSE;
	
	@OneToMany(mappedBy = "vendaCompraLojaVirtual", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ItemVendaLoja> itemVendaLojas = new ArrayList<ItemVendaLoja>();

}
