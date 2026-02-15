@echo off
echo Setting up database...
"C:\Program Files\MariaDB 12.2\bin\mysql.exe" -u root -pjamal2004 < "resource\database\EditorDBQuery.sql" > db_setup.log 2>&1
if %errorlevel% neq 0 (
    echo Database setup failed.
    type db_setup.log
    exit /b 1
)
echo Database setup success.
