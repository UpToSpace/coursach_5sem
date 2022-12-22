@echo off
echo backup database in progress
echo -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
RMAN target sys/Vv1542139 @Y:\full_backup_database.rman;
pause;