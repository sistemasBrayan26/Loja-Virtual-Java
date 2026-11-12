package jdev.mentoria.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Produto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Unidade obrigatória")
	@Column(nullable = false)
	private String tipoUnidade;
	
	@Size(min = 10, message = "Necessário ao menos 10 caracteres para o nome")
	@NotBlank(message = "Nome é obrigatório")
	@Column(nullable = false, unique = true)
	private String nome;
	
	@Column(nullable = false)
	private Boolean ativo = Boolean.TRUE;
	
	@NotBlank(message = "Descrição é obrigatória")
	@Column(columnDefinition = "text", length = 2000, nullable = false)
	private String descricao;
	
	@Positive(message = "Peso nao deve ser negativo")
	@NotNull(message = "Peso é obrigatório")
	@Column(nullable = false)
	private BigDecimal peso;
	
	@Positive(message = "Largura nao deve ser negativa")
	@NotNull(message = "Largura é obrigatória")
	@Column(nullable = false)
	private BigDecimal largura;
	
	@Positive(message = "Altura nao deve ser negativa")
	@NotNull(message = "Altura é obrigatória")
	@Column(nullable = false)
	private BigDecimal altura;
	
	@Positive(message = "Profundidade nao deve ser negativa")
	@NotNull(message = "Profundidade é obrigatória")
	@Column(nullable = false)
	private BigDecimal profundidade;
	
	@Positive(message = "Valor de venda nao deve ser negativa")
	@NotNull(message = "Valor de Venda é obrigatória")
	@Column(nullable = false)
	private BigDecimal valorVenda = BigDecimal.ZERO;
	
	@Positive(message = "Qtde em estoque nao deve ser negativa")
	@NotNull(message = "Qtde em estoque é obrigatória")
	@Column(nullable = false)
	private Integer qtdEstoque = 0;

	private Integer qtdAlertaEstoque = 0;

	private String linkYoutube;

	private Boolean alertaQtdeEstoque = Boolean.FALSE;

	private Integer qtdeClique = 0;
	
	@NotNull(message = "A empresa é obrigatória")
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;
	
	@NotNull(message = "A Categoria é obrigatória")
	@ManyToOne(targetEntity = CategoriaProduto.class)
	@JoinColumn(name = "categoria_produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "categoria_produto_id_fk"))
	private CategoriaProduto categoriaProduto = new CategoriaProduto();
	
	@NotNull(message = "A marca é obrigatória")
	@ManyToOne(targetEntity = MarcaProduto.class)
	@JoinColumn(name = "marca_produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "marca_produto_id_fk"))
	private MarcaProduto marcaProduto = new MarcaProduto();
	
	@OneToMany(mappedBy = "produto", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ImagemProduto> imagens = new ArrayList<ImagemProduto>();

}
