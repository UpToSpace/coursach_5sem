----------insert values---------------
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
insert into userroles(name) values ('admin');
insert into userroles(name) values ('user');

insert into users(email, username, password, role_id) values ('Valerie143@mail.ru', 'admin1', '1111', 1);
insert into users(email, username, password, role_id) values ('aaa', 'user1', '2222', 2);
insert into users(email, username, password, role_id) values ('bbb', 'admin2', '3333', 1);
insert into users(email, username, password, role_id) values ('ccc', 'user2', '4444', 2);

SELECT * FROM user_tables where TABLE_NAME = 'USERS';

SELECT * FROM users where email='Valerie143@mail.ru';

delete from users where email='Valerie143@mail.ru';

select * from userroles;
select * from users;
select * from authors;
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

                                select COLLECTIONS.ID, COLLECTIONS.NAME, COLLECTIONS.EMAIL, PICTURE_VIEW.ID as Pictures_id,
                PICTURE_VIEW.NAME as Picture_name, PICTURE_VIEW.AUTHOR_NAME, PICTURE_VIEW.CATEGORIES_NAME, PICTURE_VIEW.YEAR,
                PICTURE_VIEW.INFO, PICTURE_VIEW.PICTURE from COLLECTIONS
                right join collection_pictures on collections.id = COLLECTION_ID
                join picture_view on COLLECTION_PICTURES.PICTURE_ID = PICTURE_VIEW.ID
                where upper('user1') = upper(collections.email) order by COLLECTION_ID;

