----------drop tables-----------------
drop table userroles;
drop table users;
drop table authors;
drop table categories;
drop table pictures;
drop table collections;
drop table collection_pictures;

----------create tables---------------

create table userroles (
    id number(10) GENERATED AS IDENTITY
        (START WITH 1 INCREMENT BY 1),
    name varchar2(50),
    CONSTRAINT roles_pk PRIMARY KEY (id)
);

create table users (
    email varchar2(100),
    username varchar2(100) not null,
    password varchar2(255) not null,
    role_id number(10),
    CONSTRAINT users_pk PRIMARY KEY (email),
    constraint role_fk foreign key (role_id) references userroles(id)
);

-------------------------

create table authors (
    id number(10) GENERATED AS IDENTITY
    (START WITH 1 INCREMENT BY 1),
    name varchar2(100),
    info varchar2(2000),
    CONSTRAINT authors_pk PRIMARY KEY (id)
);

create table categories (
    id number(10) GENERATED AS IDENTITY
    (START WITH 1 INCREMENT BY 1),
    name varchar2(100),
    info varchar2(2000),
    CONSTRAINT categories_pk PRIMARY KEY (id)
);

create table pictures (
    id number(10) GENERATED AS IDENTITY
    (START WITH 1 INCREMENT BY 1),
    name varchar2(255),
    author_id number(10),
    category_id number(10),
    year number(10),
    info varchar2(3000),
    CONSTRAINT pictures_pk PRIMARY KEY (id),
    constraint author_fk foreign key (id) references authors(id),
    constraint category_fk foreign key (id) references categories(id)
);

create table collections (
    id number(10) GENERATED AS IDENTITY
    (START WITH 1 INCREMENT BY 1),
    name varchar2(255),
    email varchar2(255),
    CONSTRAINT collections_pk PRIMARY KEY (id),
    constraint users_fk foreign key (email) references users(email)
);

create table collection_pictures (
    id number(10) GENERATED AS IDENTITY
    (START WITH 1 INCREMENT BY 1),
    collection_id number(10),
    picture_id number(10),
    CONSTRAINT collection_pictures_pk PRIMARY KEY (id),
    constraint collection_fk foreign key (id) references collections(id),
    constraint picture_fk foreign key (id) references pictures(id)
);

----------insert values---------------
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
insert into userroles(name) values ('admin');
insert into userroles(name) values ('user');

insert into users(email, username, password, role_id) values ('Valerie143@mail.ru', 'admin1', '$2a$10$VAa19f0tRjip1omFvbSkoujs2/cMh8LGWUo9VJlKAcChmoatI8mtO', 1);

SELECT * FROM user_tables where TABLE_NAME = 'USERS';

SELECT * FROM users where email='Valerie143@mail.ru';

delete from users where email='Valerie143@mail.ru';

select * from userroles;

--------procedures---------------------

ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
SELECT * FROM user_procedures where upper(procedure_name) like upper('%register_user%'); 
select * from user_objects where object_name like upper('%register_user%');

-- registration
create or replace procedure register_user
(user_email users.email%type,
username users.username%type,
user_password users.password%type)
is
user_count number;
begin
select count(*) into user_count from users where upper(user_email) = upper(email);
if (user_count = 0) then
insert into users(email, username, password, role_id) values (user_email, username, user_password, 2);
commit;
else
raise_application_error(-20001, 'user already exists');
end if;
end register_user;

--login
create or replace procedure log_in_user
(user_email in users.email%type,
user_password in users.password%type,
o_user_email out users.email%type,
o_username out users.username%type)
is 
cursor user_cursor is select email, username from users
where upper(user_email) = upper(users.email) and upper(user_password) = upper(users.password);
begin
open user_cursor;
fetch user_cursor into o_user_email, o_username;
if user_cursor%notfound then
raise_application_error(-20000, 'user doesnt exist');
end if;
close user_cursor;
end log_in_user; 

----search pictures
create or replace procedure search_picture
(










