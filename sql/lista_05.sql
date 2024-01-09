drop database if exists lista_05;
create database if not exists lista_05;

use lista_05;

create table paciente(
	id int primary key auto_increment,
    nome varchar(150) not null,
    cpf char(14) not null unique,
    doenca varchar(50),
    index idx_nome (nome)
);

create table medico(
	id int primary key auto_increment,
    nome varchar(150) not null,
    matricula int not null unique,
    especialidade varchar(50),
    salario decimal(6,2),
	index idx_nome (nome)
);

create table consulta(
	horario datetime not null,
    valor decimal(5,2),
    id_paciente int not null,
	id_medico int not null,
     primary key(id_paciente, id_medico, horario),
    foreign key(id_paciente) references paciente(id)
		on update cascade on delete restrict,
	foreign key(id_medico) references medico(id)
		on update cascade on delete restrict,
	index idx_medico_horario (id_medico, horario), index idx_paciente_horario (id_paciente, horario)
);

insert into medico(nome, matricula, especialidade, salario) values ('Felipe', 213654, 'Cardiologista', 5000);
insert into paciente(nome, cpf, doenca) values ('Duda', 12345678910123, 'gripe');
insert into consulta(horario, valor, id_paciente, id_medico) values('2023-05-28 13:00:00', 300, 1, 1);

set sql_safe_updates = 0;

describe medico;
describe paciente;
describe consulta;

select * from medico;
select * from paciente;
select * from consulta;
