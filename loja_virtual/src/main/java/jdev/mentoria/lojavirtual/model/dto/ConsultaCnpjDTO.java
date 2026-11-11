package jdev.mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ConsultaCnpjDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<AtividadeDTO> atividadePrincipal = new ArrayList<>();
	
	private String dataSituacao;
	private String tipo;
	private String nome;
	private String uf;
	private String telefone;
	private String email;
	
	private List<AtividadeDTO> atividadeSecundaria = new ArrayList<>();
	private List<QsaDTO> qsa = new ArrayList<>();
	
	private String situacao;
	private String bairro;
	private String logradouro;
	private String numero;
	private String cep;
	private String municipio;
	private String porte;
	private String abertura;
	private String naturezaJuridica;
	private String fantasia;
	private String cnpj;
	private String ultimaAtualizacao;
	private String status;
	private String complemento;
	private String efr;
	private String motivoSituacao;
	private String situacaoEspecial;
	private String dataSituacaoEspecial;
	private String capitalSocial;
	
	private RegimeTributarioDTO simples = new RegimeTributarioDTO();
	private RegimeTributarioDTO simei = new RegimeTributarioDTO();
	private BillingDTO billing = new BillingDTO();
	
	

}
