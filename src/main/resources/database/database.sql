----------create tables---------------

create table roles (
    id INTEGER GENERATED ALWAYS AS IDENTITY primary key,
    name varchar(100)
);

create table users (
    email varchar(255) primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    role_id integer,
    constraint role_key foreign key (role_id) references roles(id)
);

----------insert values---------------

insert into roles(name) values ('admin');
insert into roles(name) values ('user');

insert into users(email, username, password, role_id) values ('Valerie143@mail.ru', 'admin1', '1111', 1);


