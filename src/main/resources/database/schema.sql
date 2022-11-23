alter session set "_ORACLE_SCRIPT"=true;

create tablespace museum
    datafile 'C:\tablespaces\museum.dbf'
    size 10 m
    autoextend on next 5 m;

select tablespace_name, status, 
    contents logging 
    from dba_tablespaces;

CREATE ROLE admin_role;

grant connect,
    create table,
    drop any table,
    create view,
    drop any view,
    create procedure, 
    drop any procedure,
    create SEQUENCE,
    drop any SEQUENCE,
    create TRIGGER,
    drop any trigger,
    EXECUTE ON sys.dbms_crypto
    TO admin_role;
    
create profile admin_profile limit 
    password_life_time 180
    sessions_per_user 3
    failed_login_attempts 7
    password_lock_time 1
    password_reuse_time 10
    password_grace_time default 
    connect_time 200
    IDLE_TIME 100

create user lera 
    IDENTIFIED BY 1234
    default tablespace museum quota unlimited on museum
    PROFILE admin_profile
    ACCOUNT UNLOCK;

grant admin_role to u1_kvs_pdb;
commit;
------drop-------
drop user u1_kvs_pdb; 
    
