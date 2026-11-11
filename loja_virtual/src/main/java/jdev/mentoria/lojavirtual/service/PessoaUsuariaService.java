package jdev.mentoria.lojavirtual.service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jdev.mentoria.lojavirtual.ExceptionMentoria;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.model.Endereco;
import jdev.mentoria.lojavirtual.model.PessoaFisica;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import jdev.mentoria.lojavirtual.model.Usuario;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.repository.PessoaRepository;
import jdev.mentoria.lojavirtual.repository.UsuarioRepository;

@Service
public class PessoaUsuariaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private ServiceSendEmail servicoEnvioEmail;
	
	@Transactional
	public PessoaJuridica salvarPessoaJuridicaVerificandoSeExisteUsuario(PessoaJuridica pessoaJuridica) 
			throws ExceptionMentoria {
		
		if (pessoaJuridica.isNova() && pessoaRepository.findByCnpj(pessoaJuridica.getCnpj()).isPresent()) {
			throw new ExceptionMentoria("Já existe CNPJ cadastrado com o numero: " + pessoaJuridica.getCnpj());
		}
		
		if (!pessoaJuridica.getEnderecos().isEmpty()) {
			for (Endereco endereco : pessoaJuridica.getEnderecos()) {
				endereco.setPessoa(pessoaJuridica);
				endereco.setEmpresa(pessoaJuridica);
			}
		}
		
		pessoaJuridica = pessoaRepository.save(pessoaJuridica);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmpresaIdAndLogin(pessoaJuridica.getId(),
				pessoaJuridica.getEmail());
		
		if (usuarioExistente.isEmpty()) {
			Usuario usuario = new Usuario();
			usuario.setDataSenhaAtual(LocalDate.now());
			usuario.setEmpresa(pessoaJuridica);
			usuario.setPessoa(pessoaJuridica);
			usuario.setLogin(pessoaJuridica.getEmail());
			String senha = "" + Instant.now().toEpochMilli();
			String senhaCriptografada = passwordEncoder.encode("" + Instant.now().toEpochMilli());
			usuario.setSenha(senhaCriptografada);
			
			Optional<Acesso> acessoEncontrado = acessoRepository.findByDescricao("ROLE_ADMIN");
			
			if (acessoEncontrado.isPresent()) {
				usuario.getAcessos().add(acessoEncontrado.get());
			}
			
			usuario = usuarioRepository.save(usuario);
			
			StringBuilder mensagemHtml = new StringBuilder();
			mensagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
			mensagemHtml.append("<b>Login: </b>"+pessoaJuridica.getEmail()+"<br/>");
			mensagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
			mensagemHtml.append("Obrigado!");
			
			try {
				servicoEnvioEmail.enviarEmailHtml("Acesso gerado para Loja Virtual", 
						mensagemHtml.toString(), pessoaJuridica.getEmail());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}

		return pessoaJuridica;
	}
	
	@Transactional
	public PessoaFisica salvarPessoaFisicaVerificandoSeExisteUsuario(PessoaFisica pessoaFisica) 
			throws ExceptionMentoria {
		
		if (pessoaFisica.isNova() && pessoaRepository.findByCpf(pessoaFisica.getCpf()).isPresent()) {
			throw new ExceptionMentoria("Já existe CPF cadastrado com o numero: " + pessoaFisica.getCpf());
		}
		
		if (!pessoaFisica.getEnderecos().isEmpty()) {
			for (Endereco endereco : pessoaFisica.getEnderecos()) {
				endereco.setPessoa(pessoaFisica);
				endereco.setEmpresa(pessoaFisica.getEmpresa());
			}
		}
		
		pessoaFisica = pessoaRepository.save(pessoaFisica);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmpresaIdAndLogin(pessoaFisica.getId(),
				pessoaFisica.getEmail());
		
		if (usuarioExistente.isEmpty()) {
			Usuario usuario = new Usuario();
			usuario.setDataSenhaAtual(LocalDate.now());
			usuario.setEmpresa(pessoaFisica.getEmpresa());
			usuario.setPessoa(pessoaFisica);
			usuario.setLogin(pessoaFisica.getEmail());
			String senha = "" + Instant.now().toEpochMilli();
			String senhaCriptografada = passwordEncoder.encode("" + Instant.now().toEpochMilli());
			usuario.setSenha(senhaCriptografada);
			
			Optional<Acesso> acessoEncontrado = acessoRepository.findByDescricao("ROLE_USER");
			
			if (acessoEncontrado.isPresent()) {
				usuario.getAcessos().add(acessoEncontrado.get());
			}
			
			usuario = usuarioRepository.save(usuario);
			
			StringBuilder mensagemHtml = new StringBuilder();
			mensagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
			mensagemHtml.append("<b>Login: </b>"+pessoaFisica.getEmail()+"<br/>");
			mensagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
			mensagemHtml.append("Obrigado!");
			
			try {
				servicoEnvioEmail.enviarEmailHtml("Acesso gerado para Loja Virtual", 
						mensagemHtml.toString(), pessoaFisica.getEmail());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}

		return pessoaFisica;
	}

}
