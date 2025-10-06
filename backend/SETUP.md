# GeoReport Backend - Spring Boot Setup Guide

## Prerequisites Check

### 1. Java Installation
Check if Java is installed:
```powershell
java -version
```

You need **Java 17 or higher**. If not installed:
- Download from: https://adoptium.net/
- Install and verify again

### 2. Maven Installation (Optional)
Check if Maven is installed:
```powershell
mvn -version
```

**If Maven is not installed**, you can either:

#### Option A: Install Maven
1. Download from: https://maven.apache.org/download.cgi
2. Extract to `C:\Program Files\Apache\Maven`
3. Add to PATH:
   - System Properties → Environment Variables
   - Add to Path: `C:\Program Files\Apache\Maven\bin`
4. Verify: `mvn -version`

#### Option B: Use Maven Wrapper (Included)
We'll generate Maven wrapper files that work without Maven installation.

## Quick Setup (3 Methods)

### Method 1: Using Maven (If Installed)

```powershell
cd backend
mvn clean install
mvn spring-boot:run
```

### Method 2: Using IDE (Recommended for Development)

#### IntelliJ IDEA
1. Open IntelliJ IDEA
2. File → Open → Select `backend` folder
3. Wait for Maven to import dependencies
4. Right-click `GeoReportApplication.java`
5. Click "Run 'GeoReportApplication'"

#### Eclipse
1. Open Eclipse
2. File → Import → Existing Maven Projects
3. Select `backend` folder
4. Wait for dependencies to download
5. Right-click project → Run As → Spring Boot App

#### VS Code
1. Install "Extension Pack for Java"
2. Install "Spring Boot Extension Pack"
3. Open `backend` folder in VS Code
4. Press F5 or click Run button
5. Select "Spring Boot App"

### Method 3: Build JAR and Run

```powershell
cd backend
mvn package -DskipTests
java -jar target/georeport-backend-1.0.0.jar
```

## Step-by-Step Manual Setup

### Step 1: Navigate to Backend
```powershell
cd C:\Users\jayan\OneDrive\Desktop\Geo\backend
```

### Step 2: Verify Project Structure
Ensure these files exist:
- `pom.xml`
- `src/main/java/com/georeport/GeoReportApplication.java`
- `src/main/resources/application.properties`

### Step 3: Download Dependencies (First Time)
```powershell
mvn dependency:resolve
```

This downloads all required libraries (Spring Boot, Hibernate, SQLite, JWT, etc.)

### Step 4: Compile the Project
```powershell
mvn clean compile
```

### Step 5: Run the Application
```powershell
mvn spring-boot:run
```

### Step 6: Verify It's Running
Open browser: http://localhost:8080/api/issues

You should see: `[]` (empty array - no issues yet)

## Expected Console Output

When successful, you'll see:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.5)

...
Default admin user created: username=admin, password=admin123
Default government user created: username=gov_user, password=gov123
Default workers created
GeoReport Application started successfully!
API Base URL: http://localhost:8080/api
Database: georeport.db (SQLite)
```

## Testing the API

### 1. Test Health (No Auth Required)
```powershell
# PowerShell
Invoke-RestMethod -Uri "http://localhost:8080/api/issues" -Method GET
```

### 2. Test Login
```powershell
$body = @{
    username = "admin"
    password = "admin123"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" -Method POST -Body $body -ContentType "application/json"
```

Expected response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "admin",
  "role": "admin",
  "fullName": "System Administrator",
  "email": "admin@georeport.com"
}
```

### 3. Test Creating an Issue (With Auth)
```powershell
$token = "YOUR_TOKEN_FROM_LOGIN"
$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}
$body = @{
    title = "Test Issue"
    description = "Testing the API"
    category = "Roads"
    priority = "medium"
    latitude = 40.7128
    longitude = -74.0060
    location = "Test Location"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/issues" -Method POST -Headers $headers -Body $body
```

## Troubleshooting

### Error: "mvn command not found"
**Solution:** Use Method 2 (IDE) or install Maven

### Error: "Java version not compatible"
**Solution:** Install Java 17+
```powershell
java -version  # Check current version
```

### Error: "Port 8080 already in use"
**Solution:** Change port in `application.properties`:
```properties
server.port=8081
```

### Error: "Could not resolve dependencies"
**Solution:** Check internet connection and Maven settings
```powershell
mvn dependency:purge-local-repository
mvn clean install
```

### Error: "Database locked"
**Solution:** Delete `georeport.db` and restart
```powershell
Remove-Item georeport.db -Force
mvn spring-boot:run
```

### Error: "Failed to configure a DataSource"
**Solution:** Ensure SQLite dependency is correct in pom.xml

## Database Location

The SQLite database file `georeport.db` will be created in:
```
C:\Users\jayan\OneDrive\Desktop\Geo\backend\georeport.db
```

You can view/edit it with:
- **DB Browser for SQLite**: https://sqlitebrowser.org/
- **DBeaver**: https://dbeaver.io/

## Logs Location

Logs are printed to console. To save to file, add to `application.properties`:
```properties
logging.file.name=georeport.log
```

## Production Deployment

### Build Production JAR
```powershell
mvn clean package -DskipTests
```

This creates: `target/georeport-backend-1.0.0.jar`

### Run Production JAR
```powershell
java -jar target/georeport-backend-1.0.0.jar
```

### Run as Windows Service (Optional)
Use tools like:
- **NSSM** (Non-Sucking Service Manager)
- **WinSW** (Windows Service Wrapper)

## Environment Variables

Set these before running (optional):
```powershell
$env:SERVER_PORT="8081"
$env:JWT_SECRET="YourCustomSecretKey"
java -jar target/georeport-backend-1.0.0.jar
```

## Next Steps

1. ✅ Backend running on http://localhost:8080
2. 📁 Open frontend in browser
3. 🔐 Login with default credentials
4. 🎨 Customize as needed

## Support

- Check README.md in root directory
- Review Spring Boot documentation
- Check application logs for errors
- Verify database file exists and is accessible

Happy coding! 🚀
