@echo off
echo ===================================
echo Employee Database Setup and Run
echo ===================================

REM Check if SQLite JDBC driver exists
if not exist "sqlite-jdbc-3.44.1.0.jar" (
    echo Downloading SQLite JDBC driver...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar' -OutFile 'sqlite-jdbc-3.44.1.0.jar'"
    echo SQLite JDBC driver downloaded successfully!
) else (
    echo SQLite JDBC driver already exists.
)

echo.
echo Compiling Java files...
javac -cp ".;sqlite-jdbc-3.44.1.0.jar" *.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    echo.
    echo Starting Employee Database Application...
    echo.
    java -cp ".;sqlite-jdbc-3.44.1.0.jar" EmployeeDatabaseApp
) else (
    echo Compilation failed! Please check for errors.
    pause
)

pause
