create or replace trigger users_before_delete 
before delete on users
for each row
declare 
collections_var collections.id%type;
begin
  select id into collections_var 
  delete from collections where :old_email = collections.email;
end;

create or replace trigger pictures_before_delete
before delete on pictures
for each row
begin
alter table pictures drop constraint category_fk where pictures.author_id = :old.id;
delete pictures where pictures.author_id = :old.id;
end;

ALTER TRIGGER eval_change_trigger DISABLE;
ALTER TRIGGER eval_change_trigger ENABLE;