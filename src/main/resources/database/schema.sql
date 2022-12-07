--connect sys museum
alter session set "_ORACLE_SCRIPT"=true;

create tablespace museum
    datafile 'C:\tablespaces\museum.dbf'
    size 10 m
    autoextend on next 5 m
    maxsize 1000m;

select tablespace_name, status, 
    contents logging 
    from dba_tablespaces;

CREATE ROLE admin_role container = current;
CREATE ROLE user_role container = current;
--drop role admin_role;
--drop role user_role;

    grant connect,
    create session,
    create any table,
    alter any table,
    insert any table,
    select any table,
    update any table,
    create procedure,
    execute any procedure,
    create view,
    create indextype,
    create any sequence,
    select any sequence,
    alter session
    TO admin_role;
    
    grant EXECUTE ON sys.dbms_crypto
    to admin_role;
    
    select *
from dba_tab_privs
where table_name = 'DBMS_CRYPTO'
  and owner = 'SYS';
    
    grant create session,
    execute any procedure,
    select any table
    to user_role;
    
select * from role_sys_privs where role='ADMIN_ROLE';
select * from role_sys_privs where role='USER_ROLE';
    
create profile admin_profile limit 
    password_life_time 180
    sessions_per_user 3
    failed_login_attempts 7
    password_lock_time 1
    password_reuse_time 10
    password_grace_time default 
    connect_time 200
    IDLE_TIME 100
    container=current;
    
    create profile user_profile limit 
    password_life_time 180
    sessions_per_user 2
    failed_login_attempts 7
    password_lock_time 1
    password_reuse_time 10
    password_grace_time default 
    connect_time 200
    IDLE_TIME 100
    container=current;

create user lera 
    IDENTIFIED BY 1234
    default tablespace museum quota unlimited on museum
    PROFILE admin_profile
    ACCOUNT UNLOCK
    container=current;

grant admin_role to lera;
commit;

create user myuser 
    IDENTIFIED BY 1234
    default tablespace museum quota unlimited on museum
    PROFILE user_profile
    ACCOUNT UNLOCK
    container=current;
    
grant user_role to myuser;
commit;

--drop user lera; 

    
