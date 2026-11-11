package jdev.mentoria.lojavirtual.model;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaFisica extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	@CPF(message = "Informe um CPF válido")
	@Column(nullable = false)
	private String cpf;
	
	@NotNull(message = "Data de nascimento obrigatória")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	@Transient
	private String senhaTemp;

}
