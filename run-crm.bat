@echo off
echo Compiling CRM Application...
javac -d bin src/com/example/crm/Main.java src/com/example/crm/CRMGui.java src/com/example/crm/MainApp.java src/com/example/crm/DataStorage.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

if "%1"=="--console" (
    echo Running CRM Application in console mode...
    java -cp bin com.example.crm.CRMApp
) else (
    echo Running CRM Application with GUI...
    java -cp bin com.example.crm.MainApp
)
pause 