@echo off
echo ==================================
echo CRM Management System
echo ==================================
echo.

echo Step 1: Cleaning previous build...
if exist bin rmdir /s /q bin
mkdir bin
echo.

echo Step 2: Compiling Java files...
javac -d bin src/com/example/crm/Main.java src/com/example/crm/CRMGui.java src/com/example/crm/MainApp.java src/com/example/crm/DataStorage.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
echo Compilation successful!
echo.

echo Step 3: Running application...
echo Current directory: %CD%
java -cp bin com.example.crm.MainApp
echo.

pause 