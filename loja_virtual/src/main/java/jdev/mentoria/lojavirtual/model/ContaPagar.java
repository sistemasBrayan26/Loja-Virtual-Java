package jdev.mentoria.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jdev.mentoria.lojavirtual.model.enums.StatusContaPagar;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Data
public class ContaPagar implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Necessário informar uma descrição")
	@Column(nullable = false)
	private String descricao;
	
	@NotNull(message = "Valor total nao pode ser nulo")
	@Positive(message = "O valor total não pode ser negativa")
	@DecimalMin(value = "0.00")
	@Column(nullable = false)
	private BigDecimal valorTotal;
	
	@Positive(message = "O desconto não pode ser negativa")
	private BigDecimal valorDesconto;
	
	@NotBlank(message = "Status é obrigatório")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusContaPagar status;
	
	@NotNull(message = "Valor de vencimento nao pode ser nula")
	@Column(nullable = false)
	private LocalDate dataVencimento;
	
	@Column(nullable = false)
	private LocalDate dataPagamento;
	
	@NotNull(message = "Pessoa deve ser informada")
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "pessoa_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private Pessoa pessoa;

	@NotNull(message = "Pessoa fornecedora deve ser informada")
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "pessoa_forn_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_forn_fk"))
	private Pessoa pessoa_fornecedora;
	
	@NotNull(message = "A empresa deve ser informada")
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;

}
