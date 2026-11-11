package jdev.mentoria.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TabelaEndPointService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional
	public void atualizarEndPointPessoaFisica() {
		jdbcTemplate.execute("begin; update tabela_acesso_end_point set qtd_acesso_end_point = "
				+ "qtd_acesso_end_point + 1 where nome_end_point = 'NOME_PESSOA_FISICA'; commit; ");
	}

}
