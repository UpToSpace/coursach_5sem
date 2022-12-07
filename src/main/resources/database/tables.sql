----------drop tables-----------------
SELECT * FROM USER_CONSTRAINTS WHERE TABLE_NAME like '%pictures%';
drop table userroles cascade constraints;
drop table users cascade constraints;
drop table authors cascade constraints;
drop table categories cascade constraints;
drop table pictures cascade constraints;
drop table collections cascade constraints;
drop table collection_pictures cascade constraints;
commit;
----------create tables (admin) ---------------

create table userroles (
                           id number(10) GENERATED AS IDENTITY
                               (START WITH 1 INCREMENT BY 1 NOCYCLE ORDER),
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
                             (START WITH 1 INCREMENT BY 1 NOCYCLE ORDER),
                         name varchar2(100),
                         info varchar2(2000),
                         CONSTRAINT authors_pk PRIMARY KEY (id)
);

create table categories (
                            id number(10) GENERATED ALWAYS AS IDENTITY
                                 (START WITH 1 INCREMENT BY 1 NOCYCLE ORDER) ,
                            name varchar2(100),
                            info varchar2(2000),
                            CONSTRAINT categories_pk PRIMARY KEY (id)
);

create table pictures (
                          id number(10) GENERATED AS IDENTITY
                              (START WITH 1 INCREMENT BY 1 NOCYCLE ORDER),
                          name varchar2(255),
                          author_id number(10),
                          category_id number(10),
                          year number(10),
                          info varchar2(3000),
                          picture blob,
                          CONSTRAINT pictures_pk PRIMARY KEY (id),
                          constraint author_fk foreign key (author_id) references authors(id),
                          constraint category_fk foreign key (category_id) references categories(id)
);

create table collections (
                             id number(10) GENERATED AS IDENTITY
                                 (START WITH 1 INCREMENT BY 1 NOCYCLE ORDER),
                             name varchar2(255),
                             email varchar2(255),
                             CONSTRAINT collections_pk PRIMARY KEY (id),
                             constraint users_fk foreign key (email) references users(email)
);

create table collection_pictures (
                                     id number(10) GENERATED AS IDENTITY
                                         (START WITH 1 INCREMENT BY 1 NOCYCLE ORDER),
                                     collection_id number(10),
                                     picture_id number(10),
                                     CONSTRAINT collection_pictures_pk PRIMARY KEY (id),
                                     constraint collection_fk foreign key (id) references collections(id),
                                     constraint picture_fk foreign key (id) references pictures(id)
);
commit;
select * from user_objects;