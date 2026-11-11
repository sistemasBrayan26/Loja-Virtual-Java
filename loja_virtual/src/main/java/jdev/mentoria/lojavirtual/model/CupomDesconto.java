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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@Entity
public class CupomDesconto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cup_desc")
	private Long id;
	
	@Column(nullable = false)
	private String codigoDesconto;
	
	@Positive(message = "O valor real do desconto não pode ser negativa")
	private BigDecimal valorRealDesconto;
	
	@Positive(message = "O valor percentual de desconto não pode ser negativa")
	private BigDecimal valorPercentualDesconto;
	
	@NotNull(message = "Validade é obrigatoria")
	@Column(nullable = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataValidade;
	
	@NotNull(message = "Empresa é obrigatoria")
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;

}
