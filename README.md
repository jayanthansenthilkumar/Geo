# GeoReport - Complete Spring Boot Application

## 🎯 Overview
GeoReport is a comprehensive **Spring Boot-based** municipal issue reporting and management system with a modern frontend. The application uses **SQLite database**, **JWT authentication**, and **RESTful APIs**.

## 📁 Project Structure

```
Geo/
├── backend/                           # Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/georeport/
│   │   │   │   ├── GeoReportApplication.java    # Main application class
│   │   │   │   ├── entity/                      # JPA Entities
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── PublicUser.java
│   │   │   │   │   ├── Issue.java
│   │   │   │   │   ├── PublicComplaint.java
│   │   │   │   │   ├── Worker.java
│   │   │   │   │   └── Comment.java
│   │   │   │   ├── repository/                  # Spring Data JPA Repositories
│   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   ├── PublicUserRepository.java
│   │   │   │   │   ├── IssueRepository.java
│   │   │   │   │   ├── PublicComplaintRepository.java
│   │   │   │   │   ├── WorkerRepository.java
│   │   │   │   │   └── CommentRepository.java
│   │   │   │   ├── service/                     # Business Logic Layer
│   │   │   │   │   ├── AuthService.java
│   │   │   │   │   ├── IssueService.java
│   │   │   │   │   ├── PublicComplaintService.java
│   │   │   │   │   ├── WorkerService.java
│   │   │   │   │   └── CommentService.java
│   │   │   │   ├── controller/                  # REST API Controllers
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── IssueController.java
│   │   │   │   │   ├── PublicComplaintController.java
│   │   │   │   │   ├── WorkerController.java
│   │   │   │   │   └── CommentController.java
│   │   │   │   ├── config/                      # Configuration Classes
│   │   │   │   │   ├── SecurityConfig.java
│   │   │   │   │   ├── JwtTokenProvider.java
│   │   │   │   │   └── JwtAuthenticationFilter.java
│   │   │   │   └── dto/                         # Data Transfer Objects
│   │   │   │       ├── LoginRequest.java
│   │   │   │       ├── LoginResponse.java
│   │   │   │       ├── PublicUserRegistrationRequest.java
│   │   │   │       └── ApiResponse.java
│   │   │   └── resources/
│   │   │       └── application.properties       # Application configuration
│   │   └── test/                                # Unit tests
│   └── pom.xml                                  # Maven dependencies
│
└── frontend/                          # Frontend Application
    ├── welcome.html                   # Landing page
    ├── login.html                     # Admin/Government login
    ├── public-login.html              # Public user registration/login
    ├── dashboard.html                 # Unified role-based dashboard
    ├── analytics.html                 # Charts and statistics
    ├── workers.html                   # Worker management
    ├── public-submit.html             # Public complaint submission
    ├── app.js                         # Frontend application logic
    ├── api-config.js                  # API client configuration
    ├── styles.css                     # Global styles
    └── README.md                      # Frontend documentation
```

## 🚀 Quick Start Guide

### Prerequisites
- **Java 17** or higher
- **Maven 3.6+** (or use included Maven wrapper)
- **Modern web browser**
- **Git** (optional, for version control)

### Backend Setup

1. **Navigate to backend directory:**
   ```powershell
   cd backend
   ```

2. **Build the project:**
   ```powershell
   mvn clean install
   ```

3. **Run the application:**
   ```powershell
   mvn spring-boot:run
   ```
   
   Or build and run the JAR:
   ```powershell
   mvn package
   java -jar target/georeport-backend-1.0.0.jar
   ```

4. **Verify the backend is running:**
   - Open browser: `http://localhost:8080/api/issues`
   - You should see an empty array `[]` (if no issues yet)

### Frontend Setup

1. **Navigate to frontend directory:**
   ```powershell
   cd frontend
   ```

2. **Serve the frontend (Option 1 - Python):**
   ```powershell
   python -m http.server 5500
   ```

3. **Serve the frontend (Option 2 - Node.js):**
   ```powershell
   npx http-server -p 5500
   ```

4. **Open in browser:**
   ```
   http://localhost:5500/welcome.html
   ```

## 🔑 Default Login Credentials

### Admin User
- **Username:** `admin`
- **Password:** `admin123`
- **Role:** Administrator (full access)

### Government User
- **Username:** `gov_user`
- **Password:** `gov123`
- **Role:** Government Officer (complaint review, issue management)

### Public User
- **Registration:** Create new account via public login page
- **Role:** Public User (submit complaints, track status)

## 📡 API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/login` | Admin/Government login | No |
| POST | `/api/auth/public/login` | Public user login | No |
| POST | `/api/auth/public/register` | Public user registration | No |

### Issue Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/issues` | Get all issues | Yes |
| GET | `/api/issues/{id}` | Get issue by ID | Yes |
| GET | `/api/issues/status/{status}` | Get issues by status | Yes |
| GET | `/api/issues/category/{category}` | Get issues by category | Yes |
| GET | `/api/issues/statistics` | Get issue statistics | Yes |
| POST | `/api/issues` | Create new issue | Admin/Gov |
| PUT | `/api/issues/{id}` | Update issue | Admin/Gov |
| PUT | `/api/issues/{issueId}/assign/{workerId}` | Assign worker to issue | Admin |
| DELETE | `/api/issues/{id}` | Delete issue | Admin |

### Public Complaint Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/complaints` | Get all complaints | Admin/Gov |
| GET | `/api/complaints/{id}` | Get complaint by ID | Yes |
| GET | `/api/complaints/status/{status}` | Get complaints by status | Admin/Gov |
| GET | `/api/complaints/user/{userId}` | Get user's complaints | Yes |
| POST | `/api/complaints?userId={userId}` | Create complaint | Public |
| PUT | `/api/complaints/{id}/approve?reviewerId={id}` | Approve complaint | Admin/Gov |
| PUT | `/api/complaints/{id}/reject?reviewerId={id}` | Reject complaint | Admin/Gov |
| POST | `/api/complaints/{id}/convert` | Convert to issue | Admin/Gov |
| DELETE | `/api/complaints/{id}` | Delete complaint | Admin |

### Worker Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/workers` | Get all workers | Admin/Gov |
| GET | `/api/workers/{id}` | Get worker by ID | Admin/Gov |
| GET | `/api/workers/department/{dept}` | Get workers by department | Admin/Gov |
| GET | `/api/workers/available` | Get available workers | Admin/Gov |
| POST | `/api/workers` | Create worker | Admin |
| PUT | `/api/workers/{id}` | Update worker | Admin |
| DELETE | `/api/workers/{id}` | Delete worker | Admin |

### Comment Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/comments/issue/{issueId}` | Get comments for issue | Yes |
| POST | `/api/comments?issueId={id}` | Add comment to issue | Yes |
| DELETE | `/api/comments/{id}` | Delete comment | Yes |

## 🗄️ Database Schema

### Tables Created Automatically

1. **users** - Admin and government users
2. **public_users** - Public/citizen users
3. **issues** - Municipal issues/problems
4. **public_complaints** - Complaints from public users
5. **workers** - Municipal workers
6. **comments** - Comments on issues

The database file `georeport.db` will be created automatically in the backend directory on first run.

## 🔒 Security Features

- **JWT (JSON Web Tokens)** for stateless authentication
- **BCrypt password hashing** for secure password storage
- **Role-based access control** (RBAC)
  - Admin: Full access
  - Government: Issue and complaint management
  - Public: Personal complaint submission and tracking
- **CORS configuration** for frontend-backend communication
- **Method-level security** with `@PreAuthorize` annotations

## 🛠️ Technology Stack

### Backend
- **Spring Boot 3.1.5** - Application framework
- **Spring Data JPA** - Data persistence
- **Spring Security** - Authentication & authorization
- **Hibernate** - ORM framework
- **SQLite** - Database
- **JWT (jjwt)** - Token-based authentication
- **Lombok** - Boilerplate code reduction
- **Maven** - Build tool

### Frontend
- **HTML5** - Structure
- **CSS3** - Styling
- **JavaScript (ES6+)** - Logic
- **Leaflet.js** - Interactive maps
- **SweetAlert2** - Beautiful alerts
- **Chart.js** (in analytics) - Data visualization

## 📋 Features

### For Admin Users
- ✅ Issue management (CRUD operations)
- ✅ Worker assignment to issues
- ✅ Public complaint review and approval
- ✅ Worker management
- ✅ Analytics and statistics
- ✅ User management

### For Government Users
- ✅ View and manage issues
- ✅ Review public complaints
- ✅ Approve/reject complaints
- ✅ Convert complaints to official issues
- ✅ View analytics

### For Public Users
- ✅ Submit complaints with location
- ✅ Track complaint status
- ✅ View personal complaint history
- ✅ Add comments to issues
- ✅ Receive updates

## 🧪 Testing the Application

1. **Start the backend:**
   ```powershell
   cd backend
   mvn spring-boot:run
   ```

2. **Start the frontend:**
   ```powershell
   cd frontend
   python -m http.server 5500
   ```

3. **Test workflow:**
   - Register a public user
   - Submit a complaint with map location
   - Login as government user
   - Review and approve the complaint
   - Convert complaint to issue
   - Login as admin
   - Assign worker to the issue
   - Update issue status

## 🔧 Configuration

### Backend Configuration
Edit `backend/src/main/resources/application.properties`:

```properties
# Server port (default: 8080)
server.port=8080

# Database file location
spring.datasource.url=jdbc:sqlite:georeport.db

# JWT secret key (change for production!)
jwt.secret=YourSuperSecretKeyForJWTTokenGenerationMustBeAtLeast256BitsLongForHS256Algorithm

# JWT token expiration (milliseconds)
jwt.expiration=86400000

# CORS allowed origins
cors.allowed-origins=http://localhost:3000,http://127.0.0.1:5500,http://localhost:5500
```

### Frontend Configuration
Edit `frontend/api-config.js`:

```javascript
const API_CONFIG = {
    BASE_URL: 'http://localhost:8080/api',
    // ... endpoint configurations
};
```

## 📦 Build for Production

### Backend
```powershell
cd backend
mvn clean package -DskipTests
```
This creates `target/georeport-backend-1.0.0.jar`

### Deploy
```powershell
java -jar target/georeport-backend-1.0.0.jar
```

### Frontend
- Host the `frontend/` directory on any web server
- Update `api-config.js` with production API URL
- Consider using nginx, Apache, or cloud hosting (Netlify, Vercel)

## 🐛 Troubleshooting

### Backend won't start
- Check Java version: `java -version` (needs 17+)
- Check port 8080 is not in use
- Delete `georeport.db` and restart (will recreate)

### Frontend can't connect to backend
- Verify backend is running on port 8080
- Check CORS configuration in `application.properties`
- Check browser console for errors
- Verify API URL in `api-config.js`

### Authentication fails
- Clear browser cache and sessionStorage
- Check JWT secret is properly configured
- Verify user credentials

### Database errors
- Delete `georeport.db` file
- Restart backend (will auto-create schema)
- Check Hibernate logs in console

## 📝 Development Guidelines

### Adding New Features

1. **Create Entity:**
   ```java
   @Entity
   @Table(name = "your_table")
   public class YourEntity { ... }
   ```

2. **Create Repository:**
   ```java
   public interface YourRepository extends JpaRepository<YourEntity, Long> { }
   ```

3. **Create Service:**
   ```java
   @Service
   public class YourService { ... }
   ```

4. **Create Controller:**
   ```java
   @RestController
   @RequestMapping("/api/your-endpoint")
   public class YourController { ... }
   ```

5. **Update Frontend:**
   - Add API methods in `api-config.js`
   - Update UI in relevant HTML files
   - Add logic in `app.js`

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the MIT License.

## 👥 Support

For issues and questions:
- Check this README
- Review API documentation
- Check browser console for errors
- Review Spring Boot logs

## 🎉 Success!

Your GeoReport application is now running with:
- ✅ Complete Spring Boot backend with SQLite
- ✅ RESTful API with JWT authentication
- ✅ Role-based access control
- ✅ Interactive frontend with maps
- ✅ Full CRUD operations for all entities
- ✅ Production-ready architecture

**Happy coding! 🚀**
