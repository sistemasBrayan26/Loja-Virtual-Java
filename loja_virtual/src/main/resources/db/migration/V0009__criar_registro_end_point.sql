insert into tabela_acesso_end_point (nome_end_point, qtd_acesso_end_point) values ('NOME_PESSOA_FISICA', 0);

alter table tabela_acesso_end_point add constraint nome_end_point_unique unique(nome_end_point);