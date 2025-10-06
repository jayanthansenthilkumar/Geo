@echo off
echo ========================================
echo    GeoReport Backend Setup
echo ========================================
echo.

echo Checking Java installation...
java -version 2>nul
if errorlevel 1 (
    echo ERROR: Java is not installed!
    echo Please install Java 17 or higher from: https://adoptium.net/
    pause
    exit /b 1
)
echo Java found!
echo.

echo ========================================
echo    Option 1: Using IntelliJ IDEA
echo ========================================
echo 1. Open IntelliJ IDEA
echo 2. File -^> Open -^> Select this 'backend' folder
echo 3. Wait for dependencies to download
echo 4. Right-click GeoReportApplication.java
echo 5. Click "Run"
echo.

echo ========================================
echo    Option 2: Using VS Code
echo ========================================
echo 1. Install "Extension Pack for Java"
echo 2. Install "Spring Boot Extension Pack"
echo 3. Open this 'backend' folder in VS Code
echo 4. Press F5 or click Run button
echo.

echo ========================================
echo    Option 3: Manual Run (Advanced)
echo ========================================
echo If you have Maven installed:
echo    mvn clean install
echo    mvn spring-boot:run
echo.

echo Once started, verify at: http://localhost:8080/api/issues
echo.
echo Default credentials:
echo   Admin: admin / admin123
echo   Government: gov_user / gov123
echo.

pause
