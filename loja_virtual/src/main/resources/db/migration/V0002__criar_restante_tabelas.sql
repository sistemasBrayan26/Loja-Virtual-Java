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
	
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica (id)
);

create table categoria_produto (
	id serial primary key, 
	descricao varchar(200) not null, 
	empresa_id integer not null, 
	
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica(id)
);

alter table produto add constraint fk_categoria_produto_id foreign key (categoria_produto_id) references categoria_produto(id);

create table marca_produto (
	id serial primary key, 
	descricao varchar(200) not null, 
	empresa_id integer not null, 
	
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica(id)
);

alter table produto add constraint fk_marca_produto_id foreign key (marca_produto_id) references marca_produto(id);

create table imagem_produto (
	id serial primary key, 
	imagem_original text not null, 
	imagem_miniatura text not null, 
	produto_id integer not null,
	empresa_id integer not null, 
	
	constraint fk_produto_id foreign key (produto_id) references produto(id),
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica(id)
);

create table avaliacao_produto (
	id serial primary key, 
	nota integer not null, 
	descricao varchar(255) not null, 
	produto_id integer not null,
	pessoa_id integer not null,
	empresa_id integer not null, 
	
	constraint fk_produto_id foreign key (produto_id) references produto(id),
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica(id)
);

create table cupom_desconto (
	id serial primary key, 
	codigo_desconto varchar(60) not null, 
	valor_real_desconto decimal(16,4), 
	valor_percentual_desconto decimal(16,4),
	data_validade date not null, 
	empresa_id integer not null, 
	
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica (id)
);

create table conta_pagar (
	id serial primary key, 
	descricao varchar(255) not null,
	valor_total numeric(19,4) not null,
	valor_desconto numeric(19, 4), 
	status varchar(60) not null, 
	data_vencimento date not null, 
	data_pagamento date, 
	pessoa_id integer not null, 
	pessoa_fornecedora_id integer not null, 
	empresa_id integer not null, 
	
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica (id)
);

create table conta_receber (
	id serial primary key, 
	descricao varchar(255) not null,
	valor_total numeric(19,4) not null,
	valor_desconto numeric(19, 4), 
	status varchar(60) not null, 
	data_vencimento date not null, 
	data_pagamento date, 
	pessoa_id integer not null, 
	empresa_id integer not null, 
	
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica (id)
);

create table nota_fiscal_compra (
	id serial primary key,
	numero_nota varchar(80) not null,
	serie_nota varchar(10) not null, 
	observacao text, 
	valor_total numeric(19,4) not null, 
	valor_desconto numeric(19, 4), 
	valor_icms numeric(19,4),
	data_compra date not null, 
	pessoa_id integer not null, 
	conta_pagar_id integer not null, 
	empresa_id integer not null, 
	
	constraint fk_conta_pagar_id foreign key (conta_pagar_id) references conta_pagar(id),
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica (id)
);

create table venda_compra_loja_virtual (
	id serial primary key,
	pessoa_id integer not null, 
	endereco_entrega_id integer not null, 
	endereco_cobranca_id integer not null, 
	valor_total numeric(19,4) not null, 
	valor_desconto numeric(19,4) not null, 
	forma_pagamento_id integer not null, 
	nota_fiscal_venda_id integer, 
	cupom_desconto_id integer, 
	valor_frete numeric(19,4), 
	dias_entrega integer not null, 
	data_venda date not null, 
	data_entrega date not null, 
	empresa_id integer not null, 
	status varchar(60) not null, 
	
	constraint fk_endereco_entrega_id foreign key (endereco_entrega_id) references endereco(id),
	constraint fk_endereco_cobranca_id foreign key (endereco_cobranca_id) references endereco(id),
	constraint fk_forma_pagamento_id foreign key (forma_pagamento_id) references forma_pagamento(id),
	constraint fk_cupom_desconto_id foreign key (cupom_desconto_id) references cupom_desconto (id),
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica (id)
);

create table nota_fiscal_venda (
	id serial primary key, 
	numero varchar(60) not null, 
	serie varchar(20) not null, 
	tipo varchar(20) not null,
	chave varchar(100) not null, 
	xml text not null, 
	pdf text not null, 
	venda_compra_loja_virtual_id integer, 
	empresa_id integer not null,
	
	constraint fk_venda_compra_loja_virtual_id foreign key (venda_compra_loja_virtual_id) references venda_compra_loja_virtual(id),
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica (id)
);

alter table venda_compra_loja_virtual add constraint fk_nota_fiscal_venda_id foreign key (nota_fiscal_venda_id) references nota_fiscal_venda(id);

create table item_venda_loja (
	id serial primary key, 
	quantidade numeric(19,4) not null, 
	produto_id integer not null, 
	venda_compra_loja_virtual_id integer not null, 
	empresa_id integer not null, 
	
	constraint fk_produto_id foreign key (produto_id) references produto(id),
	constraint fk_venda_compra_loja_virtual_id foreign key (venda_compra_loja_virtual_id) references venda_compra_loja_virtual(id),
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica(id)
);

create table nota_item_produto (
	id serial primary key,
	quantidade numeric(19,4) not null, 
	nota_fiscal_compra_id integer not null, 
	produto_id integer not null, 
	empresa_id integer not null, 
	
	constraint fk_produto_id foreign key (produto_id) references produto(id),
	constraint fk_nota_fiscal_compra_id foreign key (nota_fiscal_compra_id) references nota_fiscal_compra (id),
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica(id)
);

create table status_rastreio (
	id serial primary key,
	url_rastreio text, 
	venda_compra_loja_virtual_id integer not null, 
	empresa_id integer not null, 
	constraint fk_venda_compra_loja_virtual_id foreign key (venda_compra_loja_virtual_id) references venda_compra_loja_virtual(id),
	constraint fk_empresa_id foreign key (empresa_id) references pessoa_juridica(id)
);