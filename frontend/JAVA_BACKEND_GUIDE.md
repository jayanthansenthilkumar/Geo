# 🚀 Java Spring Boot Backend Setup Guide
## GeoReport System - SQLite Database Integration

---

## 📋 Overview

This guide explains how to create a Java Spring Boot backend with SQLite database for the GeoReport system, replacing the current localStorage implementation.

---

## 🏗️ Project Structure

```
Geo-Backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/georeport/
│       │       ├── GeoReportApplication.java
│       │       ├── config/
│       │       │   └── WebConfig.java
│       │       ├── model/
│       │       │   ├── User.java
│       │       │   ├── PublicUser.java
│       │       │   ├── Issue.java
│       │       │   ├── Complaint.java
│       │       │   ├── Worker.java
│       │       │   └── Comment.java
│       │       ├── repository/
│       │       │   ├── UserRepository.java
│       │       │   ├── PublicUserRepository.java
│       │       │   ├── IssueRepository.java
│       │       │   ├── ComplaintRepository.java
│       │       │   ├── WorkerRepository.java
│       │       │   └── CommentRepository.java
│       │       ├── service/
│       │       │   ├── UserService.java
│       │       │   ├── IssueService.java
│       │       │   ├── ComplaintService.java
│       │       │   └── WorkerService.java
│       │       └── controller/
│       │           ├── AuthController.java
│       │           ├── IssueController.java
│       │           ├── ComplaintController.java
│       │           └── WorkerController.java
│       └── resources/
│           ├── application.properties
│           └── schema.sql
├── pom.xml
└── README.md
```

---

## 📦 Dependencies (pom.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.5</version>
        <relativePath/>
    </parent>
    
    <groupId>com.georeport</groupId>
    <artifactId>georeport-backend</artifactId>
    <version>1.0.0</version>
    <name>GeoReport Backend</name>
    <description>Spring Boot backend for GeoReport Tamil Nadu</description>
    
    <properties>
        <java.version>17</java.version>
    </properties>
    
    <dependencies>
        <!-- Spring Boot Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <!-- Spring Boot Data JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        
        <!-- SQLite JDBC Driver -->
        <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.43.0.0</version>
        </dependency>
        
        <!-- Hibernate SQLite Dialect -->
        <dependency>
            <groupId>org.hibernate.orm</groupId>
            <artifactId>hibernate-community-dialects</artifactId>
        </dependency>
        
        <!-- Lombok (optional, for cleaner code) -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        
        <!-- Spring Boot DevTools -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## ⚙️ Configuration

### application.properties

```properties
# Server Configuration
server.port=8080
spring.application.name=georeport-backend

# SQLite Database Configuration
spring.datasource.url=jdbc:sqlite:georeport.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.datasource.username=
spring.datasource.password=

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# CORS Configuration
cors.allowed-origins=http://localhost:3000,http://127.0.0.1:3000,file://

# Logging
logging.level.com.georeport=DEBUG
logging.level.org.springframework.web=INFO
```

---

## 📊 Database Models

### 1. User.java (Admin/Government/Supervisor)

```java
package com.georeport.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password; // Use BCrypt in production
    
    @Column(nullable = false)
    private String role; // admin, supervisor, government
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String email;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
```

### 2. PublicUser.java

```java
package com.georeport.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "public_users")
public class PublicUser {
    @Id
    private String id; // PU1001, PU1002, etc.
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(unique = true, nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String password;
    
    private String address;
    private String city;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "total_complaints")
    private Integer totalComplaints = 0;
    
    @Column(name = "resolved_complaints")
    private Integer resolvedComplaints = 0;
}
```

### 3. Issue.java

```java
package com.georeport.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "issues")
public class Issue {
    @Id
    private String id; // TN1001, TN1002, etc.
    
    @Column(nullable = false)
    private String type;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false, length = 1000)
    private String description;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private Double lat;
    
    @Column(nullable = false)
    private Double lng;
    
    @Column(nullable = false)
    private String status = "Pending"; // Pending, In Progress, Resolved
    
    @Column(nullable = false)
    private String priority = "Medium"; // Low, Medium, High
    
    @Column(name = "reported_by", nullable = false)
    private String reportedBy;
    
    @Column(name = "reported_at")
    private LocalDateTime reportedAt = LocalDateTime.now();
    
    @Column(name = "assigned_to")
    private String assignedTo;
    
    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;
    
    @Column(name = "estimated_resolution")
    private LocalDateTime estimatedResolution;
    
    @Column(name = "source_complaint_id")
    private String sourceComplaintId;
    
    @Column(name = "complainant_phone")
    private String complainantPhone;
    
    @Column(name = "complainant_email")
    private String complainantEmail;
}
```

### 4. Complaint.java

```java
package com.georeport.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "public_complaints")
public class Complaint {
    @Id
    private String id; // PC2001, PC2002, etc.
    
    @Column(name = "user_id")
    private String userId;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private String type;
    
    @Column(nullable = false, length = 1000)
    private String description;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private Double lat;
    
    @Column(nullable = false)
    private Double lng;
    
    @Column(nullable = false)
    private String priority = "Medium";
    
    @Column(nullable = false)
    private String status = "Submitted"; // Submitted, Under Review, Approved, Rejected, Converted
    
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt = LocalDateTime.now();
    
    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;
    
    @Column(name = "reviewed_by")
    private String reviewedBy;
    
    @Column(name = "review_notes", length = 1000)
    private String reviewNotes;
    
    @Column(name = "converted_to_issue_id")
    private String convertedToIssueId;
    
    @Column(name = "reported_by", nullable = false)
    private String reportedBy;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String email;
}
```

---

## 🔌 REST API Endpoints

### Authentication Endpoints

```
POST   /api/auth/login              - Login (admin/gov/supervisor)
POST   /api/auth/register-public    - Register public user
POST   /api/auth/login-public       - Login public user
GET    /api/auth/me                 - Get current user info
POST   /api/auth/logout             - Logout
```

### Issue Endpoints

```
GET    /api/issues                  - Get all issues
GET    /api/issues/{id}             - Get issue by ID
POST   /api/issues                  - Create new issue
PUT    /api/issues/{id}             - Update issue
DELETE /api/issues/{id}             - Delete issue
PUT    /api/issues/{id}/assign      - Assign issue to worker
GET    /api/issues/statistics       - Get issue statistics
```

### Complaint Endpoints

```
GET    /api/complaints              - Get all complaints
GET    /api/complaints/{id}         - Get complaint by ID
GET    /api/complaints/user/{userId}- Get user's complaints
POST   /api/complaints              - Create new complaint
PUT    /api/complaints/{id}/review  - Review complaint
POST   /api/complaints/{id}/convert - Convert to issue
GET    /api/complaints/statistics   - Get complaint statistics
```

### Worker Endpoints

```
GET    /api/workers                 - Get all workers
GET    /api/workers/{id}            - Get worker by ID
POST   /api/workers                 - Create new worker
PUT    /api/workers/{id}            - Update worker
```

---

## 🎯 Sample Controller

### AuthController.java

```java
package com.georeport.controller;

import com.georeport.model.User;
import com.georeport.model.PublicUser;
import com.georeport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Configure properly in production
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        User user = userService.authenticate(username, password);
        
        if (user != null) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "user", user,
                "message", "Login successful"
            ));
        }
        
        return ResponseEntity.status(401).body(Map.of(
            "success", false,
            "message", "Invalid credentials"
        ));
    }
    
    @PostMapping("/register-public")
    public ResponseEntity<?> registerPublic(@RequestBody PublicUser userData) {
        try {
            PublicUser user = userService.registerPublicUser(userData);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "user", user,
                "message", "Registration successful"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    @PostMapping("/login-public")
    public ResponseEntity<?> loginPublic(@RequestBody Map<String, String> credentials) {
        String emailOrPhone = credentials.get("emailOrPhone");
        String password = credentials.get("password");
        
        PublicUser user = userService.authenticatePublicUser(emailOrPhone, password);
        
        if (user != null) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "user", user,
                "message", "Login successful"
            ));
        }
        
        return ResponseEntity.status(401).body(Map.of(
            "success", false,
            "message", "Invalid credentials"
        ));
    }
}
```

---

## 🔄 Frontend Integration

### Update app.js to use API

Replace localStorage calls with fetch API:

```javascript
// Example: Login function
async function login(username, password) {
    const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    });
    
    const data = await response.json();
    
    if (data.success) {
        sessionStorage.setItem('currentUser', JSON.stringify(data.user));
        return { success: true, user: data.user };
    }
    
    return { success: false, message: data.message };
}

// Example: Get all issues
async function getAllIssues() {
    const response = await fetch('http://localhost:8080/api/issues');
    return await response.json();
}

// Example: Create issue
async function createIssue(issueData) {
    const response = await fetch('http://localhost:8080/api/issues', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(issueData)
    });
    
    return await response.json();
}
```

---

## 🚀 How to Run

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Steps

1. **Create Spring Boot Project**
   ```bash
   # Using Spring Initializr (https://start.spring.io/)
   # Or use IDE wizard
   ```

2. **Add Dependencies to pom.xml**
   ```bash
   # Copy the pom.xml content above
   ```

3. **Create Package Structure**
   ```bash
   mkdir -p src/main/java/com/georeport/{model,repository,service,controller,config}
   ```

4. **Copy Model Classes**
   ```bash
   # Add all entity classes to model/ folder
   ```

5. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

6. **Test API**
   ```bash
   # Server runs on http://localhost:8080
   # Test with: curl http://localhost:8080/api/issues
   ```

---

## 📝 Complete Example Files

### Complete AuthController (Full Version)

See the Spring Boot documentation for complete controller examples with:
- Session management
- JWT tokens (recommended)
- Input validation
- Error handling
- Security filters

---

## 🔐 Production Security

### Must Implement:

1. **Password Hashing**
   ```java
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;
   
   String hashedPassword = passwordEncoder.encode(plainPassword);
   ```

2. **JWT Tokens**
   ```java
   // Use Spring Security + JWT
   implementation 'io.jsonwebtoken:jjwt:0.9.1'
   ```

3. **CORS Configuration**
   ```java
   @Configuration
   public class WebConfig implements WebMvcConfigurer {
       @Override
       public void addCorsMappings(CorsRegistry registry) {
           registry.addMapping("/api/**")
                   .allowedOrigins("https://yourdomain.com")
                   .allowedMethods("GET", "POST", "PUT", "DELETE");
       }
   }
   ```

4. **Input Validation**
   ```java
   @Valid @RequestBody User user
   ```

5. **HTTPS Only**

---

## ✅ Benefits of Java Backend

| Feature | localStorage | Java + SQLite |
|---------|-------------|---------------|
| Data Persistence | Browser only | Server-side |
| Multi-user | ❌ | ✅ |
| Scalability | Low | High |
| Security | Low | High (with proper config) |
| Concurrent Access | ❌ | ✅ |
| Data Integrity | Low | High |
| API Access | ❌ | ✅ |
| Mobile App Support | ❌ | ✅ |

---

## 🎯 Migration Path

### Phase 1: Current (LocalStorage)
- ✅ Quick prototyping
- ✅ No server needed
- ✅ Works immediately

### Phase 2: Java Backend (Optional)
- ✅ Add Spring Boot backend
- ✅ Keep frontend same
- ✅ Replace fetch calls
- ✅ Proper database

### Phase 3: Production (Future)
- ✅ PostgreSQL/MySQL
- ✅ JWT authentication
- ✅ Cloud deployment
- ✅ Mobile apps

---

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [SQLite JDBC Driver](https://github.com/xerial/sqlite-jdbc)
- [Hibernate SQLite Dialect](https://github.com/hibernate/hibernate-orm)

---

## 💡 Quick Start Commands

```bash
# Create Spring Boot project
curl https://start.spring.io/starter.zip \
  -d dependencies=web,data-jpa,lombok,devtools \
  -d baseDir=georeport-backend \
  -d bootVersion=3.1.5 \
  -d javaVersion=17 \
  -o georeport-backend.zip

# Extract and navigate
unzip georeport-backend.zip
cd georeport-backend

# Add SQLite dependency to pom.xml manually

# Run
mvn spring-boot:run
```

---

**Status**: 📖 Documentation Complete  
**Implementation**: Optional (Frontend works standalone)  
**Recommendation**: Use current localStorage for development, implement Java backend for production

---

**Next Steps:**
1. ✅ Use current unified dashboard (works now!)
2. ⏳ Implement Java backend (when ready for production)
3. ⏳ Migrate fetch calls from localStorage to API
4. ⏳ Deploy backend to server
