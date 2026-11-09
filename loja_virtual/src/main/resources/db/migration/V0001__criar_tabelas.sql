create table acesso (
	
	id serial primary key, 
	descricao varchar(150),
	empresa_id integer not null;
	
);

create table pessoa_juridica (
	
	id serial primary key,
	nome varchar(60) not null, 
	email varchar(100) not null, 
	telefone varchar(30) not null, 
	tipo_pessoa varchar(30) not null,
	cnpj varchar(20) not null, 
	inscricao_estadual varchar(20) not null, 
	inscricao_municipal varchar(20),
	fantasia varchar(60) not null,
	razao varchar(60) not null,
	categoria varchar(60),
	empresa_id integer not null;
	
);

create table pessoa_fisica (
	
	id serial primary key,
	nome varchar(60) not null, 
	email varchar(100) not null, 
	telefone varchar(30) not null, 
	tipo_pessoa varchar(30) not null,
	cpf varchar(20) not null, 
	data_nascimento date,
	empresa_id integer not null;
	
);

alter table acesso add constraint empresa_id_fk foreign key (empresa_id) references (pessoa_juridica);
alter table pessoa_fisica add constraint empresa_id_fk foreign key (empresa_id) references (pessoa_juridica);
alter table pessoa_juridica add constraint empresa_id_fk foreign key (empresa_id) references (pessoa_juridica);

create table usuario (
	id serial primary key, 
	login varchar(100) not null,
	senha varchar(30) not null, 
	data_senha_atual date not null,
	pessoa_id integer not null, 
	empresa_id integer not null, 
	
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica(id),
	constaint uq_login unique(login);
	
);

create table usuarios_acesso (
	usuario_id integer not null, 
	acesso_id integer not null, 
	
	constraint fk_usuario_id foreign key (usuario_id) references usuario(id),
	constraint fk_acesso_id foreign key (acesso_id) references acesso(id);
);

create table forma_pagamento (
	id serial primary key, 
	descricao varchar(100), 
	empresa_id integer not null, 
	
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica(id);
);

create table endereco (
	id serial primary key,
	logradouro varchar(150) not null, 
	numero varchar(60) not null, 
	bairro varchar(100) not null, 
	cep varchar(15) not null, 
	complemento varchar(255), 
	cidade varchar(100) not null, 
	uf varchar(2) not null, 
	estado varchar(100) not null,
	tipo_endereco varchar(20) not null,
	pessoa_id integer not null, 
	empresa_id integer not null, 
	
	constraint fk_pessoa_id foreign key (empresa_id) references pessoa_juridica(id);
);



