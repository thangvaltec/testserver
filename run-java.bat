@echo off
REM Script to compile and run Java test - Silent mode

REM Set console to UTF-8 for proper Japanese character display
chcp 65001 > nul

REM Check if java exists
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Error: Java not found!
    echo Please install Java JDK first.
    pause
    exit /b 1
)

REM Compile silently
javac -encoding UTF-8 TestApiJava.java 2>nul

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

REM Run the test
java TestApiJava

pause
