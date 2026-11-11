package jdev.mentoria.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
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
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Data
public class NotaFiscalCompra implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nª Nota é obrigatória")
	@Column(nullable = false)
	private String numeroNota;
	
	@NotBlank(message = "Serie da nota é obrigatoria")
	@Column(nullable = false)
	private String serieNota;
	
	private String observacao;
	
	@NotNull(message = "Valor total nao pode ser nulo")
	@Positive(message = "O valor total não pode ser negativa")
	@DecimalMin(value = "0.00")
	@Column(nullable = false)
	private BigDecimal valorTotal;
	
	@Positive(message = "O desconto deve não deve ser negativo")
	private BigDecimal valorDesconto;
	
	@NotNull(message = "Valor ICMS nao pode ser nulo")
	@Positive(message = "O valor ICMS não pode ser negativo")
	@DecimalMin(value = "0.00")
	@Column(nullable = false)
	private BigDecimal valorIcms;
	
	@NotNull(message = "Data da compra deve ser informada")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(nullable = false)
	private LocalDate dataCompra;
	
	@NotNull(message = "Pessoa deve ser informada")
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "pessoa_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private Pessoa pessoa;
	
	@NotNull(message = "Conta a pagar deve ser informada")
	@ManyToOne
	@JoinColumn(name = "conta_pagar_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "conta_pagar_fk"))
	private ContaPagar contaPagar;
	
	@NotNull(message = "Empresa deve ser informada")
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;

}
