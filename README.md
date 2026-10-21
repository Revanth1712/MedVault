# MedVault - Secure Medical Records Management System

MedVault is a healthcare-focused backend application designed to help users securely manage their medical records through REST APIs. The project provides user registration and authentication, medical-record management, search, categorization, sorting, and pagination.

The backend is built with Java, Spring Boot, Spring Security, Spring Data JPA, and MySQL.

## Features

- **User Management:** Register users and authenticate with email and password.
- **Medical Record Management:** Create, retrieve, update, and delete medical records.
- **User-Specific Access:** Associate medical records with their respective authenticated users.
- **Search:** Find medical records by patient name.
- **Category Filtering:** Retrieve records by record type.
- **Sorting:** Retrieve medical records sorted by date.
- **Pagination:** Retrieve records in pages for more efficient data access.
- **Record Statistics:** Retrieve total and category-specific record counts.
- **Input Validation:** Validate incoming request data.
- **Persistent Storage:** Store application data in MySQL using Spring Data JPA.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 25 | Programming language |
| Spring Boot 4.1.1 | Application framework |
| Spring Web MVC | REST API development |
| Spring Security | Authentication and endpoint protection |
| Spring Data JPA | Database persistence |
| Hibernate | ORM |
| MySQL | Relational database |
| Maven | Dependency and build management |
| Jakarta Validation | Request validation |

## Project Structure

```text
MedVault/
â”œâ”€â”€ .mvn/
â”œâ”€â”€ src/
â”‚   â”œâ”€â”€ main/
â”‚   â”‚   â”œâ”€â”€ java/com/medvault/
â”‚   â”‚   â”‚   â”œâ”€â”€ CustomAuthenticationEntryPoint.java
â”‚   â”‚   â”‚   â”œâ”€â”€ CustomUserDetailsService.java
â”‚   â”‚   â”‚   â”œâ”€â”€ GlobalExceptionHandler.java
â”‚   â”‚   â”‚   â”œâ”€â”€ HealthController.java
â”‚   â”‚   â”‚   â”œâ”€â”€ MedicalRecord.java
â”‚   â”‚   â”‚   â”œâ”€â”€ MedicalRecordController.java
â”‚   â”‚   â”‚   â”œâ”€â”€ MedicalRecordRepository.java
â”‚   â”‚   â”‚   â”œâ”€â”€ MedicalRecordService.java
â”‚   â”‚   â”‚   â”œâ”€â”€ MedvaultApplication.java
â”‚   â”‚   â”‚   â”œâ”€â”€ PasswordConfig.java
â”‚   â”‚   â”‚   â”œâ”€â”€ SecurityConfig.java
â”‚   â”‚   â”‚   â”œâ”€â”€ User.java
â”‚   â”‚   â”‚   â”œâ”€â”€ UserController.java
â”‚   â”‚   â”‚   â”œâ”€â”€ UserRepository.java
â”‚   â”‚   â”‚   â””â”€â”€ UserService.java
â”‚   â”‚   â””â”€â”€ resources/
â”‚   â”‚       â””â”€â”€ application.properties
â”‚   â””â”€â”€ test/
â”œâ”€â”€ pom.xml
â”œâ”€â”€ mvnw
â”œâ”€â”€ mvnw.cmd
â””â”€â”€ .gitignore
```

## Getting Started

### Prerequisites

Install the following:

- Java Development Kit (JDK) 25
- MySQL Server
- Git

Maven is available through the included Maven Wrapper scripts.

### 1. Clone the Repository

```bash
git clone https://github.com/Revanth1712/MedVault.git
cd MedVault
```

### 2. Create the Database

Open your MySQL client and execute:

```sql
CREATE DATABASE medvault;
```

### 3. Configure Database Credentials

The application reads its database username and password from environment variables.

In Windows PowerShell, set the credentials in the terminal you will use to run the application:

```powershell
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "YOUR_MYSQL_PASSWORD"
```

Replace `YOUR_MYSQL_PASSWORD` with your own MySQL password. Use a MySQL account with appropriate permissions for the `medvault` database.

Do not commit real database credentials to GitHub.

### 4. Run the Application

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

The application is configured to run at:

```text
http://localhost:8081
```

## API Overview

The base URL for local development is `http://localhost:8081`.

### User APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/users/register` | Register a new user |
| POST | `/api/users/login` | Authenticate a user |

Registration and login are permitted without prior authentication. Other endpoints require authentication.

### Medical Record APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/medical-records` | Create a medical record |
| GET | `/api/medical-records` | Retrieve the authenticated user's records |
| GET | `/api/medical-records/count` | Get the user's total record count |
| GET | `/api/medical-records/{id}` | Retrieve a record by ID |
| PUT | `/api/medical-records/{id}` | Update a record |
| DELETE | `/api/medical-records/{id}` | Delete a record |
| GET | `/api/medical-records/search?patientName={name}` | Search records by patient name |
| GET | `/api/medical-records/category/{recordType}` | Filter records by category |
| GET | `/api/medical-records/category/{recordType}/count` | Count records in a category |
| GET | `/api/medical-records/category/{recordType}/paginated?page=0&size=5` | Retrieve a category's records in pages |
| GET | `/api/medical-records/paginated?page=0&size=5` | Retrieve records in pages |
| GET | `/api/medical-records/sorted-by-date` | Retrieve records sorted by date |

Protected endpoints use the authentication configured by Spring Security. The current login API returns a success message; it does not return a JWT token.

## Security Notes

- Protected API endpoints require authentication.
- Medical-record operations are scoped to the authenticated user's identity in the controller and service layer.
- Request validation is applied to relevant inputs.
- Database credentials should be supplied through environment variables.
- Use synthetic data for development and testing.
- Before deploying, review HTTPS, CSRF configuration, authorization rules, error handling, and production database settings.

**Important:** This repository is a development project. Do not use it to store real patient information until its security, privacy, access controls, and applicable healthcare compliance requirements have been independently reviewed.

## Testing

Run the project's automated tests with:

```powershell
.\mvnw.cmd test
```

## Future Improvements

Potential areas for further development include:

- Frontend integration
- API documentation with Swagger/OpenAPI
- Expanded automated integration and security tests
- Production deployment and monitoring
- Improved documentation and contribution guidelines

These are potential improvements, not claims that the features are already implemented.

## Contributing

Contributions and suggestions are welcome.

1. Fork the repository.
2. Create a feature branch.
3. Make and test your changes.
4. Commit your changes with a descriptive message.
5. Open a pull request describing the changes.

## Author

**Sai Revanth Sattenapalli**

GitHub: [@Revanth1712](https://github.com/Revanth1712)

## License

No license has been specified yet. Until a license is added to the repository, reuse and redistribution permissions should not be assumed.

