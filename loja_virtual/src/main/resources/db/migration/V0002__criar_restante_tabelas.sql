create table produto (
	id serial primary key, 
	tipo_unidade varchar(4) not null, 
	nome varchar(100) not null, 
	ativo boolean not null default true, 
	descricao text not null, 
	peso decimal(10, 3) not null, 
	largura decimal(10, 3) not null, 
	altura decimal(10, 3) not null, 
	profundidade decimal(10, 3) not null, 
	valor_venda decimal(19, 4) not null default 0.00, 
	qtd_estoque integer not null default 0, 
	qtd_alerta_estoque integer default 0, 
	link_youtube text, 
	alerta_qtde_estoque boolean default false, 
	qtde_clique integer default 0,
	empresa_id integer not null,
	categoria_produto_id integer not null,
	marca_produto_id integer not null,
	
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica (id);
);

create table categoria_produto (
	id serial primary key, 
	descricao varchar(200) not null, 
	empresa_id integer not null, 
	
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica(id);
);

alter table produto add constraint fk_categora_produto_id foreign key (categoria_produto_id) references categoria_produto(id);

create table marca_produto (
	id serial primary key, 
	descricao varchar(200) not null, 
	empresa_id integer not null, 
	
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica(id);
);

alter table produto add constraint fk_marca_produto_id foreign key (marca_produto_id) references marca_produto(id);

create table imagem_produto (
	id serial primary key, 
	imagem_original text not null, 
	imagem_miniatura text not null, 
	produto_id integer not null,
	empresa_id integer not null, 
	
	constraint fk_produto_id foreign key (produto_id) references produto(id),
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica(id);
	
);

create table avaliacao_produto (
	id serial primary key, 
	nota integer not null, 
	descricao varchar(255) not null, 
	produto_id integer not null,
	pessoa_id integer not null,
	empresa_id integer not null, 
	
	constraint fk_produto_id foreign key (produto_id) references produto(id),
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica(id);
	
);

