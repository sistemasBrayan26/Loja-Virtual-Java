package jdev.mentoria.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;

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

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Data
public class ItemVendaLoja implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_fiscal_venda")
	private Long id;
	
	@NotNull(message = "Quantidade é obrigatória")
	@Positive(message = "A quantidade não pode ser negativa")
	@DecimalMin(value = "0.01", message = "Valor minimo da quantidade deve ser maior que zero")
	@Column(nullable = false)
	private BigDecimal quantidade;

	@NotNull(message = "Produto é obrigatório")
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "produto_fk"))
	private Produto produto;

	@NotNull(message = "Venda deve ser informada")
	@ManyToOne
	@JoinColumn(name = "venda_compraLoja_virtu_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "venda_compraLoja_virtu_fk"))
	private VendaCompraLojaVirtual vendaCompraLojaVirtual;
	
	@NotNull(message = "Empresa é obrigatória")
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;

}
