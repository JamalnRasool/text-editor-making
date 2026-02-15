@echo off
if not exist bin mkdir bin
echo Finding sources...
if exist sources.txt del sources.txt
for /R src %%f in (*.java) do echo "%%f" >> sources.txt
echo Compiling...
javac -d bin -cp "resource/*" @sources.txt
if %errorlevel% neq 0 (
    echo Compilation failed!
    exit /b 1
)
echo Compilation successful.
del sources.txt
