# GeoReport - Quick Start Script
# Run this script to start both backend and frontend

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   GeoReport Application Startup" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check Java
Write-Host "Checking Java installation..." -ForegroundColor Yellow
$javaVersion = java -version 2>&1 | Select-String "version"
if ($javaVersion) {
    Write-Host "✓ Java found: $javaVersion" -ForegroundColor Green
} else {
    Write-Host "✗ Java not found! Please install Java 17 or higher" -ForegroundColor Red
    exit 1
}

# Check Maven
Write-Host "Checking Maven installation..." -ForegroundColor Yellow
$mavenVersion = mvn -version 2>&1 | Select-String "Apache Maven"
if ($mavenVersion) {
    Write-Host "✓ Maven found: $mavenVersion" -ForegroundColor Green
} else {
    Write-Host "✗ Maven not found! Please install Maven 3.6 or higher" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   Starting Backend (Spring Boot)" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

# Start backend in new window
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$PSScriptRoot\backend'; Write-Host 'Building and starting Spring Boot application...' -ForegroundColor Green; mvn spring-boot:run"

Write-Host "✓ Backend starting in new window..." -ForegroundColor Green
Write-Host "  URL: http://localhost:8080/api" -ForegroundColor Cyan
Write-Host ""

# Wait for backend to start
Write-Host "Waiting for backend to start (30 seconds)..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   Starting Frontend (HTTP Server)" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

# Check for Python
$pythonExists = Get-Command python -ErrorAction SilentlyContinue
if ($pythonExists) {
    Write-Host "✓ Starting frontend with Python HTTP server..." -ForegroundColor Green
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$PSScriptRoot\frontend'; Write-Host 'Frontend server starting...' -ForegroundColor Green; python -m http.server 5500"
    Write-Host "  URL: http://localhost:5500/welcome.html" -ForegroundColor Cyan
} else {
    Write-Host "! Python not found. Please manually serve the frontend directory" -ForegroundColor Yellow
    Write-Host "  You can use: python -m http.server 5500" -ForegroundColor Cyan
    Write-Host "  Or: npx http-server -p 5500" -ForegroundColor Cyan
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   Application Started!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Backend API: http://localhost:8080/api" -ForegroundColor Cyan
Write-Host "Frontend:    http://localhost:5500/welcome.html" -ForegroundColor Cyan
Write-Host ""
Write-Host "Default Credentials:" -ForegroundColor Yellow
Write-Host "  Admin: admin / admin123" -ForegroundColor White
Write-Host "  Government: gov_user / gov123" -ForegroundColor White
Write-Host "  Public: Register new account" -ForegroundColor White
Write-Host ""
Write-Host "Press any key to exit..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
