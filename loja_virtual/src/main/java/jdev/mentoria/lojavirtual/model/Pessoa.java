package jdev.mentoria.lojavirtual.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdev.mentoria.lojavirtual.model.enums.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_seq_gen")
	@SequenceGenerator(name = "pessoa_seq_gen", sequenceName = "pessoa_seq", allocationSize = 1)
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	@Column(nullable = false)
	private String nome;
	
	@Email(message = "Por favor informe um e-mail valido")
	@NotBlank(message = "E-mail é obrigatório")
	@Column(nullable = false)
	private String email;
	
	@NotBlank(message = "Telefone é obrigatório")
	@Column(nullable = false)
	private String telefone;
	
	@NotNull(message = "Tipo é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoPessoa tipoPessoa;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id", 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;
	
	@OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Endereco> enderecos = new ArrayList<Endereco>();
	
	public boolean isNova() {
		return this.id == null;
	}


}
