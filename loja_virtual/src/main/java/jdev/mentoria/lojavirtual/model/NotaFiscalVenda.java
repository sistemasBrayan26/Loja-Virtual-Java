package jdev.mentoria.lojavirtual.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Data
public class NotaFiscalVenda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_fiscal_venda")
	private Long id;

	@NotBlank(message = "Nª Nota é obrigatória")
	@Column(nullable = false)
	private String numero;

	@NotBlank(message = "Serie da nota é obrigatoria")
	@Column(nullable = false)
	private String serie;

	@NotBlank(message = "Tipo da Nota é obrigatório")
	@Column(nullable = false)
	private String tipo;

	@NotBlank(message = "Chave da nota é obrigatoria")
	@Column(nullable = false)
	private String chave;
	
	@NotBlank(message = "XML da nota é obrigatoria")
	@Column(columnDefinition = "text", nullable = false)
	private String xml;

	@NotBlank(message = "PDF da nota é obrigatoria")
	@Column(columnDefinition = "text", nullable = false)
	private String pdf;
	
	@NotNull(message = "Venda deve ser informada na nota")
	@OneToOne
	@JoinColumn(name = "venda_compra_loja_virt_id", nullable = true, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "venda_compra_loja_virt_fk"))
	private VendaCompraLojaVirtual vendaCompraLojaVirtual;
	
	@NotNull(message = "A empresa é obrigatória")
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;

}
