create table pacientes(
id_paciente bigint not null auto_increment primary key,
nombre varchar(100) not null,
email varchar(100) not null unique,
documento_identidad varchar(14) not null unique,
telefono varchar(20) not null,
calle varchar(100) not null,
distrito varchar(100) not null,
complemento varchar(100),
numero varchar(20),
ciudad varchar(100) not null,

activo tinyint not null

);

create table consultas(
id_consulta bigint not null auto_increment primary key,
id_medico BINARY(16) not null,
id_paciente bigint not null,
fecha datetime not null,

constraint fk_medico foreign key (id_medico) references medicos(id_medico),
constraint fk_paciente foreign key (id_paciente) references pacientes(id_paciente)
);