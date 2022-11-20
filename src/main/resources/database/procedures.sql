--------procedures---------------------

ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
SELECT * FROM user_procedures where upper(procedure_name) like upper('%register_user%');
select * from user_objects where object_name like upper('%add%');

--encrypt password
CREATE OR REPLACE FUNCTION encrypt_password
(i_password in users.password%type)
    RETURN users.password%type
    IS
    my_key VARCHAR2(2000) := '2022202220222022';
    my_val VARCHAR2(2000) := i_password;
    my_mod NUMBER := DBMS_CRYPTO.encrypt_aes128 + DBMS_CRYPTO.chain_cbc + DBMS_CRYPTO.pad_pkcs5;
    encrypted_password RAW(2000);
BEGIN
    encrypted_password := DBMS_CRYPTO.encrypt(utl_i18n.string_to_raw(my_val, 'AL32UTF8'), my_mod, utl_i18n.string_to_raw(my_key, 'AL32UTF8'));
    RETURN RAWTOHEX(encrypted_password);
END encrypt_password;

--decrypt password
CREATE OR REPLACE FUNCTION decrypt_password
(i_password in users.password%type)
    return users.password%type
    IS
    my_key VARCHAR2(2000) := '2022202220222022';
    my_val RAW(2000) := HEXTORAW(i_password);
    my_mod NUMBER := DBMS_CRYPTO.encrypt_aes128 + DBMS_CRYPTO.chain_cbc + DBMS_CRYPTO.pad_pkcs5;
    decrypted_password RAW(2000);
BEGIN
    decrypted_password := DBMS_CRYPTO.decrypt(my_val, my_mod, utl_i18n.string_to_raw(my_key, 'AL32UTF8'));
    RETURN utl_i18n.raw_to_char(decrypted_password);
END decrypt_password;

-- registration
create or replace procedure register_user
(i_email users.email%type,
i_username users.username%type,
i_password users.password%type)
is
user_count number;
begin
select count(*) into user_count from users where upper(i_email) = upper(email);
if (user_count = 0) then
insert into users(email, username, password, role_id) values (i_email, i_username, encrypt_password(i_password), 2);
commit;
else
raise_application_error(-20001, 'user already exists');
end if;
end register_user;


--login
create or replace procedure log_in_user
(i_email in users.email%type,
i_password in users.password%type,
o_username out users.username%type,
o_role_id out users.ROLE_ID%type,
o_role_name out USERROLES.name%type
)
is
cursor user_cursor is select ROLE_ID, username from users
                      where upper(i_email) = upper(users.email) and upper(i_password) = upper(decrypt_password(users.password));
begin
open user_cursor;
fetch user_cursor into o_role_id, o_username;
if user_cursor%notfound then
raise_application_error(-20000, 'user doesnt exist');
end if;
select userroles.name into o_role_name from USERROLES where o_role_id = USERROLES.ID;
close user_cursor;
end log_in_user;

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
        update users set users.USERNAME = i_username, users.PASSWORD = encrypt_password(i_password) where upper(i_email) = upper(users.email);
        commit;
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
i_info in pictures.info%type,
i_picture in pictures.picture%type)
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
        insert into pictures(name, author_id, category_id, year, info, PICTURE) values (i_name, author_id, category_id, i_year, i_info, i_picture);
        commit;
else
        raise_application_error(-20004, 'picture already exists');
end if;
end add_picture;


-----------------------------


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
(i_id in pictures.id%type)
    is
    picture_count number;
begin
    select count(*) into picture_count from pictures where PICTURES.ID = i_id;
    if (picture_count = 1) then
        delete pictures where PICTURES.ID = i_id;
    else
        raise_application_error(-20009, 'cannot delete, picture doesnt exist');
    end if;
end delete_picture;

-------------------
--search pictures by name
drop function search_picture;

create or replace function search_picture
(i_name in PICTURES.name%type)
 return sys_refcursor
is
    o_picture_cursor sys_refcursor;
begin
    open o_picture_cursor for select * from PICTURE_VIEW where upper(NAME) like upper('%' || i_name|| '%');
--close o_picture_cursor;
    return o_picture_cursor;
end;

--- add collection
create or replace procedure add_collection
(i_name in COLLECTIONS.name%type,
 i_email in USERS.EMAIL%type)
    is
    collection_count number;
begin
    select count(*) into collection_count from collections where upper(collections.name) = upper(i_name);
    if(collection_count = 0) then
        insert into collections(name, email) values (i_name, i_email);
        commit;
    else
        raise_application_error(-20010, 'collection already exists');
    end if;
end add_collection;

-- delete collection
create or replace procedure delete_collection
(i_id in collections.id%type)
    is
    collection_count number;
begin
    select count(*) into collection_count from collections where COLLECTIONS.ID = i_id;
    if(collection_count = 1) then
        delete collections where COLLECTIONS.ID = i_id;
        commit;
    else
        raise_application_error(-20011, 'cannot delete, author doesnt exist');
    end if;
end delete_collection;

--- add pictures to user's collection
create or replace procedure add_picture_to_collection
(i_picture_id in PICTURES.ID%type,
 i_email in USERS.EMAIL%type)
    is
    picture_count number;
    collection_id COLLECTIONS.ID%type;
begin
    select count(*) into picture_count from collection_pictures where i_picture_id = COLLECTION_PICTURES.PICTURE_ID;
    if(picture_count = 0) then
        select COLLECTIONS.ID into collection_id from collections where upper(i_email) = upper(collections.email);
        insert into COLLECTION_PICTURES(collection_id, picture_id) values (collection_id, i_picture_id);
        commit;
    else
        raise_application_error(-20012, 'collection doesnt exist');
    end if;
end add_picture_to_collection;

--- delete pictures from user's collection
--- 