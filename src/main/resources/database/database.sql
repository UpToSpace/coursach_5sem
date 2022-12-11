----------delete from tables----------
delete from users;


----------insert values---------------
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
insert into userroles(name) values ('admin');
insert into userroles(name) values ('user');

----------users------------
declare 
a number(10, 0) := 0;
begin
while(a < 1000000)
loop
register_user('email' || a, 'username' || a, a || a || a || a); 
a := a + 1;
end loop;
end;

-------authors-------------
declare 
a number(10, 0) := 0;
begin
while(a < 1000000)
loop
add_author('name' || a, 'info' || a); 
a := a + 1;
end loop;
end;

--------

insert into users(email, username, password, role_id) values ('Valerie143@mail.ru', 'admin1', ENCRYPT_PASSWORD('1111'), 1);
insert into users(email, username, password, role_id) values ('aaa', 'user1', ENCRYPT_PASSWORD('2222'), 4);
insert into users(email, username, password, role_id) values ('bbb', 'admin2', ENCRYPT_PASSWORD('3333'), 3);
insert into users(email, username, password, role_id) values ('ccc', 'user2', ENCRYPT_PASSWORD('4444'), 4);

SELECT * FROM user_tables where TABLE_NAME = 'USERS';

SELECT * FROM users where email='Valerie143@mail.ru';

delete pictures where id = 1;

select * from userroles;
select * from users;
select * from authors where name = 'name405';
select * from categories;
select * from pictures;
select * from collections;
select * from collection_pictures;

select * from full_user_userrole_view where upper(email) = upper('Valerie143@mail.ru');
select * from full_user_userrole_view where upper(email) = upper('bbb');

insert into AUTHORS(name, info) values ('a', 'auth info');
insert into CATEGORIES(name, info) values ('c', 'infoooo');



insert into pictures(name, author_id, category_id, year, info)
values('itsname', 1, 1, 2002, 'myinfo');


commit;

begin
    IMPORT_XML();
end;


                select COLLECTIONS.ID, COLLECTIONS.NAME, COLLECTIONS.EMAIL, PICTURE_VIEW.ID as Pictures_id,
                PICTURE_VIEW.NAME as Picture_name, PICTURE_VIEW.AUTHOR_NAME, PICTURE_VIEW.CATEGORIES_NAME, PICTURE_VIEW.YEAR,
                PICTURE_VIEW.INFO, PICTURE_VIEW.PICTURE from COLLECTIONS
                full join collection_pictures on collections.id = COLLECTION_ID
                join picture_view on COLLECTION_PICTURES.PICTURE_ID = PICTURE_VIEW.ID
                where upper('email1') = upper(collections.email) order by COLLECTION_ID;
                
