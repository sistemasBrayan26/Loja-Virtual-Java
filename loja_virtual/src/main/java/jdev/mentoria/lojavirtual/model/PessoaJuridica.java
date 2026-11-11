package jdev.mentoria.lojavirtual.model;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaJuridica extends Pessoa {
	
	private static final long serialVersionUID = 1L;
	
	@CNPJ(message = "Informar um CNPJ válidp")
	@Column(nullable = false)
	private String cnpj;
	
	@NotBlank(message = "Inscrição Estadual obrigatória")
	@Column(nullable = false)
	private String inscricaoEstadual;
	
	private String inscricaoMunicipal;
	
	@NotBlank(message = "Nome fantasia obrigatória")
	@Column(nullable = false)
	private String fantasia;
	
	@NotBlank(message = "Razão Social obrigatória")
	@Column(nullable = false)
	private String razao;
	
	private String categoria;

}
