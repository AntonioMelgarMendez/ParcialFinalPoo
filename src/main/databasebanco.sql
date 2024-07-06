create database dbSistemaBanco;
use dbSistemaBanco;

create table cliente (
	idCliente int not null primary key,
	nombre varchar(25),
	apellido varchar(25),
	direccion varchar(255),
	telefono char(8)
);

create table tarjeta (
	numTarjeta char(16) not null primary key,
	facilitador varchar(25),
	tipoTarjeta char(1),
	idCliente int,
	foreign key (idCliente) references cliente(idCliente) on delete cascade
);

create table transaccion (
	idTransaccion int not null primary key,
	fecha_compra date,
	totalMonto decimal (20,2),
	descripcion varchar(255),
	idCliente int,
	foreign key (idCliente) references cliente(idCliente) on delete cascade
);

insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (1, 'Darlyn', 'Donis', 'Calle Bayunca', '12345678');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (2, 'Daniel', 'Mendez', 'Calle Botella', '23456789');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (3, 'Yael', 'Herrera', 'Calle Disenio', '34567890');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (4, 'Walter', 'Ramirez', 'Calle Lapicero', '98765432');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (5, 'Gabriel', 'Aguilar', 'Calle Sillon', '87654321');

insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (1234567890123456, 'MasterCard', 1, 'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (2345678901234567, 'Visa', 2, 'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (3456789012345678, 'AmericanExpress', 3, 'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (4567890123456789, 'Visa', 4, 'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (5678901234567890, 'Visa', 5, 'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (6789012345678901, 'AmericanExpress', 1, 'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (7890123456789012, 'AmericanExpress', 1, 'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (8901234567890123, 'MasterCard', 3, 'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (9012345678901234, 'AmericanExpress', 5, 'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (0123456789012345, 'MasterCard', 5, 'C');

insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (1, '2024-01-12', 13.45, 'McDonalds', 1);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (2, '2024-05-21', 32.79, 'Jugueton', 2);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (3, '2024-12-03', 9.99, 'Libreria', 2);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (4, '2024-11-07', 3.75, 'Uber', 5);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (5, '2024-06-22', 24999.99, 'Grupo Q', 3);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (6, '2024-03-28', 27.99, 'RadioShack', 1);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (7, '2024-02-01', 47.99, 'Excel Automotriz', 1);


select * from Cliente;
select * from tarjeta;
select * from transaccion;

select c.idCliente, c.nombre, c.apellido, t.fecha_compra, t.totalMonto from transaccion t inner join Cliente c on c.idCliente = t.idCliente inner join tarjeta tr on tr.idCliente = c.idCliente where t.idCliente = 1 and fecha_compra between '2024-01-01' and '2024-02-01';