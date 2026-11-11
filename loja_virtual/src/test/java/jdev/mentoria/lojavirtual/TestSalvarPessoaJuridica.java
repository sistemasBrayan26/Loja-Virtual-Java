package jdev.mentoria.lojavirtual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jdev.mentoria.lojavirtual.controller.PessoaController;
import jdev.mentoria.lojavirtual.model.Endereco;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import jdev.mentoria.lojavirtual.model.enums.TipoEndereco;
import jdev.mentoria.lojavirtual.model.enums.TipoPessoa;

@SpringBootTest
public class TestSalvarPessoaJuridica {
	
	@Autowired
	private PessoaController pessoaController;
	
	@Test
	public void deveSalvarPessoaComUsuarioPadrao() throws ExceptionMentoria {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCategoria("ADMINISTRADOR");
		pessoaJuridica.setCnpj("89.711.538/0001-18");
		pessoaJuridica.setEmail("brayan.iub10@gmail.com");
		pessoaJuridica.setFantasia("Joana e Roberto Casa Noturna Ltda");
		pessoaJuridica.setInscricaoEstadual("11.944.101-2");
		pessoaJuridica.setInscricaoMunicipal("ISENTO");
		pessoaJuridica.setNome("JOANA E ROBERTO");
		pessoaJuridica.setRazao("Joana e Roberto Casa Noturna Ltda");
		pessoaJuridica.setTelefone("(62) 2948-6904");
		pessoaJuridica.setTipoPessoa(TipoPessoa.JURIDICA);
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Travessa Alto do Monte");
		endereco.setNumero("100");
		endereco.setBairro("Planalto");
		endereco.setCep("59073-214");
		endereco.setCidade("Natal");
		endereco.setEstado("Rio Grande do Norte");
		endereco.setUf("RN");
		endereco.setTipoEndereco(TipoEndereco.COBRANCA);
		
		Endereco enderecoEntrega = new Endereco();
		enderecoEntrega.setLogradouro("Avenida Anhanguera");
		enderecoEntrega.setNumero("1000");
		enderecoEntrega.setBairro("Rodoviário");
		enderecoEntrega.setCep("74430-030");
		enderecoEntrega.setCidade("Goiânia");
		enderecoEntrega.setEstado("Goias");
		enderecoEntrega.setUf("GO");
		enderecoEntrega.setTipoEndereco(TipoEndereco.ENTREGA);
		
		pessoaJuridica.getEnderecos().add(enderecoEntrega);
		pessoaJuridica.getEnderecos().add(endereco);
		
		
		pessoaJuridica = pessoaController.salvarPJ(pessoaJuridica).getBody();
		
		assertEquals(true, pessoaJuridica.getId() > 0);
		
	}

}
