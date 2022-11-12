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

    -----------------------------

---- delete user
create or replace procedure delete_user
(i_email in users.email%type)
is
user_count number;
begin
select count(*) into user_count from users where upper(i_email) = upper(users.EMAIL);
if (user_count = 1) then
delete users where upper(i_email) = upper(users.email);
else
raise_application_error(-20005, 'user doesnt exist');
end if;
end delete_user;

---- update user
create or replace procedure update_user
(i_email in users.email%type,
i_username in users.username%type,
i_password in users.password%type)
    is
    user_count number;
begin
    select count(*) into user_count from users where upper(i_email) = upper(users.EMAIL);
    if (user_count = 1) then
        update users set users.USERNAME = i_username, users.PASSWORD = i_password where upper(i_email) = upper(users.email);
    else
        raise_application_error(-20006, 'cannot update user because it doesnt exist');
        end if;
end update_user;

----add author
create or replace procedure add_author
(i_name in authors.name%type,
i_info in authors.info%type)
is
author_count number;
begin
select count(*) into author_count from authors where upper(authors.name) = upper(i_name);
if(author_count = 0) then
    insert into authors(name, info) values (i_name, i_info);
commit;
else
        raise_application_error(-20002, 'author already exists');
end if;
end add_author;

----add category
create or replace procedure add_category
(i_name in categories.name%type,
 i_info in categories.info%type)
    is
    category_count number;
begin
select count(*) into category_count from categories where upper(categories.name) = upper(i_name);
if(category_count = 0) then
        insert into categories(name, info) values (i_name, i_info);
commit;
else
        raise_application_error(-20003, 'category already exists');
end if;
end add_category;

--add picture
create or replace procedure add_picture
(i_name in pictures.name%type,
i_author_name in authors.name%type,
i_category_name in categories.name%type,
i_year in pictures.year%type,
i_info in pictures.info%type)
is
picture_count number;
author_id authors.id%type;
category_id categories.id%type;
begin
select authors.id into author_id from authors where upper(i_author_name) = upper(authors.name);
select categories.id into category_id from categories where upper(i_category_name) = upper(categories.name);
select count(*) into picture_count from pictures where upper(pictures.name) = upper(i_name)
                                                   and pictures.author_id = author_id;
if (picture_count = 0) then
        insert into pictures(name, author_id, category_id, year, info) values (i_name, author_id, category_id, i_year, i_info);
else
        raise_application_error(-20004, 'picture already exists');
end if;
end add_picture;


--delete category
create or replace procedure delete_category
(i_name in categories.name%type)
    is
    category_count number;
begin
    select count(*) into category_count from categories where upper(categories.name) = upper(i_name);
    if(category_count = 1) then
        delete categories where upper(categories.name) = upper(i_name);
        commit;
    else
        raise_application_error(-20007, 'cannot delete category doesnt exist');
    end if;
end delete_category;

--delete author
create or replace procedure delete_author
(i_name in authors.name%type)
    is
    author_count number;
begin
    select count(*) into author_count from authors where upper(authors.name) = upper(i_name);
    if(author_count = 1) then
        delete authors where upper(authors.name) = upper(i_name);
        commit;
    else
        raise_application_error(-20008, 'cannot delete, author doesnt exist');
    end if;
end delete_author;

--delete picture
create or replace procedure delete_picture
(i_name in pictures.name%type,
 i_author_name in authors.name%type)
    is
    picture_count number;
    author_id authors.id%type;
begin
    select authors.id into author_id from authors where upper(i_author_name) = upper(authors.name);
    select count(*) into picture_count from pictures where upper(pictures.name) = upper(i_name)
                                                       and pictures.author_id = author_id;
    if (picture_count = 1) then
        delete pictures where upper(pictures.name) = upper(i_name) and pictures.author_id = author_id;
    else
        raise_application_error(-20009, 'cannot delete, picture doesnt exist');
    end if;
end delete_picture;
-------------------