package jdev.mentoria.lojavirtual.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.ExceptionMentoria;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.model.Endereco;
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
	
	@Transactional
	public PessoaJuridica salvarPessoaJuridicaVerificandoSeExisteUsuario(PessoaJuridica pessoaJuridica) 
			throws ExceptionMentoria {
		
		if (pessoaJuridica == null) {
			throw new ExceptionMentoria("Não foi informado nenhum dado a respeito da Pessoa Juridica");
		}
		
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
			usuario.setSenha(passwordEncoder.encode("" + Instant.now().toEpochMilli()));
			
			Optional<Acesso> acessoEncontrado = acessoRepository.findByDescricao("ROLE_USER");
			
			if (acessoEncontrado.isPresent()) {
				usuario.getAcessos().add(acessoEncontrado.get());
			}
			
			usuario = usuarioRepository.save(usuario);
			
		}

		return pessoaJuridica;
	}

}
