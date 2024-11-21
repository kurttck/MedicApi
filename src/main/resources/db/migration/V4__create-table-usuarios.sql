

create table usuarios(

    id_usuario bigint not null auto_increment primary key,
    login varchar(100) not null unique,
    clave varchar(300) not null
);