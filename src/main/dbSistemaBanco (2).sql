create database dbSistemaBanco;
drop database dbSistemaBanco
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
	foreign key (idCliente) references cliente(idCliente) on delete cascade,
);

insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (1,  'Tarja',					'Regancin',			'Korversberg 144',				'85950683');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (2,  'Kesirat',				'Wieczorek',		'Brixtonlaan 111',				'83739263');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (3,  'John',					'Kucharska',		'280 M. Sfakianaki',			'56743155');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (4,  'Zikoranachukwudimma',	'Oono',				'153 Block Road',				'55548931');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (5,  'Gara',					'Chandler',			'Clius 66',						'41045132');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (6,  'Matthildur',				'Braat',			'R Afonso Albuquerque 27',		'20822447');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (7,  'Maria',					'Lothran',			'ul. 1 Maja 117',				'94671962');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (8,  'Valeriya',				'Isokoski',			'Messedamm 83',					'70027470');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (9,  'Xiu',					'Noguchi',			'Vest 109',						'58800042');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (10, 'Annika',					'Rumble',			'Rua Caio Jos de Miranda 542',	'94880626');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (11, 'Shahsuvar',				'Kjelstad',			'C/ Benito Guinea 60',			'64412812');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (12, 'Patric',					'Eggertsdttir',		'Via Medina 113',				'87972090');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (13, 'Joshua',					'Wu',				'Akurbraut 22',					'93826659');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (14, 'Quinzia',				'Riddell',			'Rua Condes Torre 24',			'87442121');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (15, 'Silvia',					'Rodrigues',		'C/ Fernndez de Leceta 19',		'49816024');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (16, 'Leandro',				'Alanis',			'Calle Aduana 53',				'86426263');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (17, 'Andi',					'Timayev',			'Rosa de los Vientos 54',		'25148104');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (18, 'Alauddin',				'Graham',			'Calvo Sotelo 48',				'98967640');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (19, 'Xia',					'He',				'Alcon Molina 48',				'41159845');
insert into Cliente(idCliente, nombre, apellido, direccion, telefono) values (20, 'Aya',					'Peitl',			'Cao 95',						'75904977');

insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (5129214197770813, 'MasterCard',		1,  'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (5197126244728262, 'MasterCard',		6,  'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (5549439223356384, 'MasterCard',		3,  'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (5193532420718288, 'MasterCard',		4,  'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (5200872179820450, 'MasterCard',		2,  'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (5193533104452624, 'MasterCard',		6,  'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (5197126530968804, 'MasterCard',		9,  'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (5200873545985910, 'MasterCard',		10, 'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (5362562708546510, 'MasterCard',		11, 'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (5200871431929547, 'MasterCard',		20, 'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (4357804458145253, 'Visa',				7,  'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (4357804431248026, 'Visa',				13, 'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (4357801438471486, 'Visa',				19, 'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (4357806670463620, 'Visa',				20, 'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (4357803468712821, 'Visa',				1,  'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (4357805655160698, 'Visa',				2,  'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (4357804362182434, 'Visa',				6,  'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (4357808424950779, 'Visa',				4,  'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (4357803698407895, 'Visa',				8,  'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (4357809557552978, 'Visa',				17, 'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (3756588503062670, 'AmericanExpress',	3,  'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (3756821109156566, 'AmericanExpress',	4,  'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (3756561266445508, 'AmericanExpress',	9,  'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (3756871734998663, 'AmericanExpress',	15, 'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (3756807999305775, 'AmericanExpress',	19, 'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (3756532721578669, 'AmericanExpress',	18, 'D');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (3756164246438985, 'AmericanExpress',	2,  'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (3756812669652457, 'AmericanExpress',	4,  'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (3756871650248887, 'AmericanExpress',	8,  'C');
insert into Tarjeta(numTarjeta, facilitador, idCliente, tipoTarjeta) values (3756513641960953, 'AmericanExpress',	16, 'C');

insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (1,  '2019-08-07', 90.90,		'Pan Palace',				1);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (2,  '2023-09-16', 46.56,		'Panoramic Goods',			2);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (3,  '2021-12-03', 5.65,		'Pandora Store',			20);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (4,  '2019-01-13', 3.75,		'Panorama Marketplace',		13);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (5,  '2020-07-12', 13.13,		'Pan Fusion',				11);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (6,  '2022-04-26', 4.03,		'CarroZone',				9);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (7,  '2022-12-18', 33.53,		'Wheels & Carro',			5);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (8,  '2024-06-01', 3.86,		'AutoMotive Carro',			14);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (9,  '2023-05-19', 75.71,		'CarroStyle Co.',			11);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (10, '2024-05-21', 18.24,		'EliteAuto Carro',			11);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (11, '2019-08-29', 94.79,		'LuxeDrive',				4);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (12, '2018-06-03', 959.26,		'Belleza Lips',				3);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (13, '2018-03-22', 355.14,		'Chic Beauty Touch',		17);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (14, '2020-06-19', 308.65,		'Belleza Lip Boutique',		18);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (15, '2023-09-04', 772.95,		'Beauty Kiss Cosmetics',	17);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (16, '2021-05-11', 293.33,		'Opulent Beauty Lipsticks', 14);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (17, '2020-10-12', 265.63,		'Mercado Finds',			7);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (18, '2021-12-12', 36.34,		'Tienda Mercado',			2);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (19, '2023-11-07', 48.31,		'Mercado Essentials',		6);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (20, '2020-04-02', 603.86,		'Mercado Haven',			9);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (21, '2021-12-19', 8384.23,		'Mercado Bliss',			10);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (22, '2020-07-01', 2243.60,		'Lacteos Haven',			20);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (23, '2024-06-26', 4840.85,		'Lacteos Elegance',			20);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (24, '2020-08-27', 3715.83,		'Lacteos Oasis',			14);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (25, '2018-02-01', 1887.24,		'Uber',						17);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (26, '2022-08-20', 24999.99,	'Grupo Q',					15);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (27, '2021-11-21', 200.73,		'Lacteos Opulence',			1);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (28, '2021-07-04', 224.83,		'Opulent Cuisines',			8);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (29, '2021-08-29', 7958.28,		'Gourmet Palace',			3);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (30, '2023-05-11', 8773.20,		'Fine Dining Hall',			3);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (31, '2023-09-12', 63169.48,	'Exclusive Dining',			4);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (32, '2024-11-07', 91330.51,	'Sumptuous Dining',			9);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (33, '2023-05-12', 54526.07,	'Luxury Feasts',			3);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (34, '2020-02-26', 63416.73,	'Restaurante Socks',		19);
insert into Transaccion(idTransaccion, fecha_compra, totalMonto, descripcion, idCliente) values (35, '2019-06-18', 2528.78,		'Glamorous Finds',			13);