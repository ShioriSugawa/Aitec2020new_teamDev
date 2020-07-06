CREATE TABLE employee (
employee_number VARCHAR(6),
employee_name VARCHAR(30),
employee_profile VARCHAR(100),
employee_deployment VARCHAR(100),
employee_year INTEGER,
employment INTEGER
);

create table career (
owned_career_id bigserial primary key,
employee_number character varying(6),
business_start character varying(7),
business_end character varying(7),
business_name character varying(100),
situation integer
);
alter table career add foreign key (employee_number) references employee (employee_number) on delete cascade;
alter table career alter business_start type character varying(7);
alter table career alter business_end type character varying(7);


create table certification_genre (
certification_genre_code varchar(3) primary key,
certification_genre_name varchar(20)
);

create table certification (
certification_code varchar(6) primary key,
certification_name varchar(100),
certification_genre_code varchar(3) ,
foreign key (certification_genre_code) references certification_genre(certification_genre_code) on delete no action on update cascade
);

create table owned_certification (
owned_certification_id bigserial primary key,
employee_number varchar(6),
certification_code varchar(6),
certification_date varcher(7),
foreign key (employee_number) references employee(employee_number) on delete cascade,
foreign key (certification_code) references certification(certification_code) on delete no action on update cascade
);

create table owned_other_certification (
owned_other_certification_id bigserial primary key,
employee_number varchar(6),
certification_genre_code varchar(6),
other_certification_date varcher(7),
other_certification_name varchar(100),
foreign key (employee_number) references employee(employee_number) on delete cascade,
foreign key (certification_genre_code) references certification_genre(certification_genre_code) on delete no action on update cascade
);

create table skill_genre (
skill_genre_code varchar(3) primary key,
skill_genre_name varchar(20)
);

create table owned_skill (
owned_skill_id bigserial primary key,
employee_number varchar(6),
skill_genre_code varchar(3),
skill_name varchar(100),
foreign key (employee_number) references employee(employee_number) on delete cascade,
foreign key (skill_genre_code) references skill_genre(skill_genre_code) on delete no action on update cascade
);
