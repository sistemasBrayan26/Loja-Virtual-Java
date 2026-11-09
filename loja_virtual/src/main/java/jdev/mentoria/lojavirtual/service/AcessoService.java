package jdev.mentoria.lojavirtual.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.ExceptionMentoria;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;

@Service
public class AcessoService {
	
	private AcessoRepository acessoRepository;
	
	@Transactional
	public Acesso registrar(Acesso acesso) throws ExceptionMentoria {
		
		if (acesso.isNovo()) {
			Optional<Acesso> acessoEncontrado = acessoRepository.findByDescricaoAndEmpresaId(acesso.getDescricao(),
					acesso.getEmpresa().getId());
			
			if (acessoEncontrado.isPresent()) {
				throw new ExceptionMentoria("Já existe acesso com a descrição");
			}
		}
		
		return acesso = acessoRepository.save(acesso);
	
	}
	
	public void deletarAcesso(Long id) throws ExceptionMentoria {
		Optional<Acesso> acesso = acessoRepository.findById(id);
		
		if (acesso.isEmpty()) {
			throw new ExceptionMentoria("Acesso não encontrado, verifique");
		}
		
		acessoRepository.delete(acesso.get());
	}

}
