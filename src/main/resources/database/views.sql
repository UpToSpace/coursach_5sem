drop view picture_view;
drop view user_userrole_view;
drop view full_user_userrole_view;

----user and role for admin (without password)
create view user_userrole_view as
select users.email, users.username, userroles.name
from users left join userroles on USERS.ROLE_ID = USERROLES.ID;

----user and role for user (with password)
create view full_user_userrole_view as
select users.email, users.username, decrypt_password(users.password) as password, userroles.name
from users left join userroles on USERS.ROLE_ID = USERROLES.ID;

commit;

----picture with author and category
drop view picture_view;
create view picture_view as
select PICTURES.NAME, PICTURES.INFO, AUTHORS.NAME as author_name, CATEGORIES.NAME as categories_name, PICTURES.ID, PICTURES.YEAR, PICTURES.PICTURE from PICTURES
left join authors on PICTURES.AUTHOR_ID = AUTHORS.ID
left join categories on PICTURES.CATEGORY_ID = CATEGORIES.ID


