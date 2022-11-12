----user and role for admin (without password)
create view user_userrole_view as
select users.email, users.username, userroles.name
from users left join userroles on USERS.ROLE_ID = USERROLES.ID;

----user and role for user (with password)
create view full_user_userrole_view as
select users.email, users.username, users.password, userroles.name
from users left join userroles on USERS.ROLE_ID = USERROLES.ID;

----picture with author and category
create view full_picture_view as
select PICTURES.NAME, PICTURES.INFO, AUTHORS.NAME, CATEGORIES.NAME from PICTURES
join authors on PICTURES.AUTHOR_ID = AUTHORS.ID
join categories on PICTURES.CATEGORY_ID = CATEGORIES.ID


