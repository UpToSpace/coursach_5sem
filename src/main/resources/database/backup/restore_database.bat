@echo off
echo orañle restore in progress...
RMAN target sys/Vv1542139 @Y:\restore_database.rman;
PAUSE;


