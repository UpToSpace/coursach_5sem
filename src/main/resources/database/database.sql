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
select * from userroles;

select * from full_user_userrole_view where upper(email) = upper('Valerie143@mail.ru');
select * from full_user_userrole_view where upper(email) = upper('bbb');

insert into AUTHORS(name, info) values ('auth name', 'auth info');
insert into CATEGORIES(name, info) values ('nameee', 'infoooo');



insert into pictures(name, author_id, category_id, year, info)
values('itsname', 1, 1, 2002, 'myinfo');


commit;



