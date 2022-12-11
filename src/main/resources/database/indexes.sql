----------------indexes--------------------
set autotrace on explain;

create index users_password_i on users(
password) COMPUTE STATISTICS;

select ROLE_ID, username from users
where upper('40404040') = upper(decrypt_password(users.password));





create index authors_i on authors(
name)


set autotrace off;
alter index index_name rebuild;
