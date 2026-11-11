package jdev.mentoria.lojavirtual.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdev.mentoria.lojavirtual.model.enums.TipoEndereco;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Data
public class Endereco implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Logradouro deve ser informado")
	@Column(nullable = false)
	private String logradouro;
	
	@NotBlank(message = "N° deve ser informado")
	@Column(nullable = false)
	private String numero;
	
	@NotBlank(message = "CEP deve ser informado")
	@Column(nullable = false)
	private String cep;
	
	@NotBlank(message = "Bairro deve ser informado")
	@Column(nullable = false)
	private String bairro;
	
	private String complemento;
	
	@NotBlank(message = "UF deve ser informado")
	@Column(nullable = false)
	private String uf;
	
	@NotBlank(message = "Cidade deve ser informada")
	@Column(nullable = false)
	private String cidade;
	
	@NotBlank(message = "Estado deve ser informado")
	@Column(nullable = true)
	private String estado;
	
	@NotBlank(message = "Tipo do Endereço deve ser informado")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoEndereco tipoEndereco;
	
	@NotNull(message = "Pessoa é obrigatória")
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private Pessoa pessoa;
	
	@NotNull(message = "Empresa é obrigatória")
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;
	
	

}
