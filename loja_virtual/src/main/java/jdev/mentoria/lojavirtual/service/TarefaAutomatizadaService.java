package jdev.mentoria.lojavirtual.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jdev.mentoria.lojavirtual.model.Usuario;
import jdev.mentoria.lojavirtual.repository.UsuarioRepository;

@Service
public class TarefaAutomatizadaService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	//@Scheduled(initialDelay = 2000, fixedDelay = 86400000)
	@Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo")
	public void notificarUsuarioTrocarSenha() throws UnsupportedEncodingException, MessagingException, InterruptedException {
		
		LocalDate dataLimite = LocalDate.now().minusDays(90);
		
		List<Usuario> usuarios = usuarioRepository.listarUsuariosComSenhasVencidas(dataLimite);
		
		for (Usuario usuario : usuarios) {
			StringBuilder msg = new StringBuilder();
			msg.append("Olá, ").append(usuario.getPessoa().getNome()).append("<br/>");
			msg.append("Está na hora de trocar sua senha, já passou 90 dias de validade.").append("<br/>");
			msg.append("troque sua senha da Loja Virtual do Brayan - Desenvolvedor Pleno");
			
			serviceSendEmail.enviarEmailHtml("Troca de Senha", msg.toString(), usuario.getLogin());
			
			Thread.sleep(3000);
		}
		
	}

}
