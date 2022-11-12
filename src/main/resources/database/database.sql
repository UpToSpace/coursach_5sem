----------insert values---------------
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
insert into userroles(name) values ('admin');
insert into userroles(name) values ('user');

insert into users(email, username, password, role_id) values ('Valerie143@mail.ru', 'admin1', '$2a$10$VAa19f0tRjip1omFvbSkoujs2/cMh8LGWUo9VJlKAcChmoatI8mtO', 1);

SELECT * FROM user_tables where TABLE_NAME = 'USERS';

SELECT * FROM users where email='Valerie143@mail.ru';

delete from users where email='Valerie143@mail.ru';

select * from userroles;








