CREATE OR REPLACE TRIGGER USERS_CHANGE_TRIGGER
  AFTER INSERT OR UPDATE OR DELETE
  ON USERS
  for each row
BEGIN
  IF INSERTING THEN
    dbms_output.put_line('Insert user ' || :new.email);
  ELSIF UPDATING THEN
    dbms_output.put_line('Update user from ' || :old.username || ', to ' || :new.username);
  ELSIF DELETING THEN
    dbms_output.put_line('Delete user ' || :old.email);
  END IF;
END;