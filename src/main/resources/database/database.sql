----------delete from tables----------
delete from users;

----------insert values---------------
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
insert into userroles(name) values ('admin');
insert into userroles(name) values ('user');

----------users------------
declare 
begin
for a in 1 .. 100
loop
register_user('email' || a, 'username' || a, a || a || a); 
end loop;
end;

-------authors-------------
declare 
begin
for a in 1 .. 100000
loop
add_author('name' || a, 'info' || a); 
end loop;
end;

-- insert admin
insert into users(email, username, password, role_id) values ('Valerie143@mail.ru', 'admin1', ENCRYPT_PASSWORD('1111'), 1);

select * from userroles;
select * from users;
select * from authors;
select * from categories;
select * from pictures;
select * from collections;
select * from collection_pictures;

commit;

--from table to file
begin
    IMPORT_XML();
end;

--from file to table
begin
    EXPORT_XML();
end;

 explain plan for SELECT name FROM authors WHERE name like '%name1%' and info like '%a%';
 select * from table(DBMS_XPLAN.DISPLAY());
 