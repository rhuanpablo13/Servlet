create database mercado;
use mercado;

create table produto(
	cdProduto int not null,
	nmProduto varchar(45) not null,
	vlPreco double (6,2) not null,
	primary key(cdProduto)
);

insert into produto (10, 'bolacha', 0.99);
insert into produto (10, 'carne moída', 9.99);
insert into produto (10, 'alface', 1.99);
insert into produto (10, 'macarrao', 4.79);