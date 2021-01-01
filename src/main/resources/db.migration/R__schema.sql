create table "user"
(
	id serial not null,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	email varchar(255) not null,
	phone varchar(255) not null,
	username varchar(255) not null
);

comment on table "user" is 'the user whom created the appointment';

create unique index user_id_uindex
	on "user" (id);

create unique index user_username_uindex
	on "user" (username);

alter table "user"
	add constraint user_pk
		primary key (id);

create table car
(
	id serial not null,
	make varchar(255) not null,
	model varchar(255) not null,
	year varchar(255) not null
);

comment on table car is 'the car that is scheduled to be worked on';

create unique index car_id_uindex
	on car (id);

alter table car
	add constraint car_pk
		primary key (id);

CREATE TYPE appointment_status AS ENUM ('pending', 'approved', 'in_progress', 'completed');

create table appointment
(
	id serial not null,
	date_time timestamp not null,
	status appointment_status not null default 'pending',
	service varchar(255) not null
);

create unique index appointment_id_uindex
	on appointment (id);

alter table appointment
	add constraint appointment_pk
		primary key (id);

alter table appointment
    add user_id int not null;

alter table appointment
    add constraint appointment_user_id_fk
        foreign key (user_id) references "user";

alter table appointment
    add car_id int not null;

alter table appointment
    add constraint appointment_car_id_fk
        foreign key (car_id) references car;

alter table car
	add user_id int;

alter table car
    add constraint car_user_id_fk
        foreign key (user_id) references user;



