----------create tables---------------

create table users (
    id INTEGER GENERATED ALWAYS AS IDENTITY,
    username varchar(255) not null,
    password varchar(255) not null,
    email varchar(320) not null unique
);

create table roles (
    id INTEGER GENERATED ALWAYS AS IDENTITY,
    name varchar(100)
);

create table user_roles (
    user_id integer,
    role_id integer,
    constraint userkey foreign key (user_id) references users(id),
    constraint rolekey foreign key (role_id) references roles(id)
);

----------insert values---------------

insert into roles(name) values ('admin');
insert into roles(name) values ('user');

insert into users(username, password, email) values ('user1', '1111', 'valerie143@mail.ru');

insert into user_roles values (1, 1);

