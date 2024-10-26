# Library Management System
The **Library Management System** is a full-stack Spring Boot application that provides a comprehensive solution for managing library resources, users, and transactions. It enables libraries to handle user registrations, book reservations, borrowing, notifications, fines, and reviews through secure RESTful APIs. This application uses JWT-based authentication and fine-grained role-based authorization, providing a robust security model for end-users and admins alike.

## Key Features

- **Secure Authentication & Authorization**: JWT-based token authentication with role-based access.
- **Book Management**: Add, update, and search for books, with real-time availability tracking.
- **Borrowing & Returns**: Borrow and return books, with automated fine calculations for overdue items.
- **Notifications**: Email reminders for reservations, due dates, and fines.
- **User Reviews**: Users can rate and review books, fostering community engagement.
- **Detailed Error Responses**: JSON responses for error handling, aiding front-end integration.

## Technology Stack

| Category                   | Technologies                                  |
|----------------------------|-----------------------------------------------|
| **Framework**              | Spring Boot                                   |
| **Security**               | Spring Security, JWT                          |
| **Database**               | PostgreSQL, Spring Data JPA                   |
| **Utilities**              | Lombok, ModelMapper, Jakarta Validation API   |
| **Email Support**          | Spring Boot Starter Mail                      |
| **JSON Handling**          | Jackson Databind                              |

[//]: # (| **Testing**                | JUnit, Mockito                                |)



## Project Structure

```
library-management-system/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── library/
│   │   │           └── management/
│   │   │               ├── config/
│   │   │               │   └── SecurityConfig.java
│   │   │               │ 
│   │   │               ├── controllers/
│   │   │               │   ├── AuthController.java
│   │   │               │   ├── BookController.java
│   │   │               │   ├── BorrowController.java
│   │   │               │   ├── FineController.java
│   │   │               │   ├── NotificationController.java
│   │   │               │   ├── ReservationController.java
│   │   │               │   ├── ReviewController.java
│   │   │               │   └── UserController.java
│   │   │               │
│   │   │               ├── dto/
│   │   │               │   ├── BookDto.java
│   │   │               │   ├── BorrowDto.java
│   │   │               │   ├── FineDto.java
│   │   │               │   ├── NotificationDto.java
│   │   │               │   ├── ReservationDto.java
│   │   │               │   ├── ReviewDto.java
│   │   │               │   └── UserDto.java
│   │   │               │
│   │   │               ├── entities/
│   │   │               │   ├── Book.java
│   │   │               │   ├── Borrow.java
│   │   │               │   ├── Fine.java
│   │   │               │   ├── Notification.java
│   │   │               │   ├── Reservation.java
│   │   │               │   ├── Review.java
│   │   │               │   ├── Role.java
│   │   │               │   └── User.java
│   │   │               │
│   │   │               ├── enums/
│   │   │               │   └── RoleName.java
│   │   │               │
│   │   │               ├── exceptions/
│   │   │               │   └── GlobalExceptionHandler.java
│   │   │               │
│   │   │               ├── jwt/
│   │   │               │   ├── AuthenticationRequest.java
│   │   │               │   ├── AuthenticationResponse.java
│   │   │               │   ├── JwtRequestFilter.java
│   │   │               │   └── JwtUtil.java
│   │   │               │
│   │   │               ├── repo/
│   │   │               │   ├── BookRepo.java
│   │   │               │   ├── BorrowRepo.java
│   │   │               │   ├── FineRepo.java
│   │   │               │   ├── NotificationRepo.java
│   │   │               │   ├── ReservationRepo.java
│   │   │               │   ├── ReviewRepo.java
│   │   │               │   ├── RoleRepo.java
│   │   │               │   └── UserRepo.java
│   │   │               │
│   │   │               ├── service/
│   │   │               │   ├── impl/
│   │   │               │   │   ├── BookServiceImpl.java
│   │   │               │   │   ├── BorrowServiceImpl.java
│   │   │               │   │   ├── EmailServiceImpl.java
│   │   │               │   │   ├── FineServiceImpl.java
│   │   │               │   │   ├── NotificationServiceImpl.java
│   │   │               │   │   ├── ReservationServiceImpl.java
│   │   │               │   │   ├── ReviewServiceImpl.java
│   │   │               │   │   └── UserServiceImpl.java
│   │   │               │   │
│   │   │               │   ├── BookService.java
│   │   │               │   ├── BorrowService.java
│   │   │               │   ├── EmailService.java
│   │   │               │   ├── FineService.java
│   │   │               │   ├── NotificationService.java
│   │   │               │   ├── ReservationService.java
│   │   │               │   ├── ReviewService.java
│   │   │               │   └── UserService.java
│   │   │               │   
│   │   │               ├── utils/
│   │   │               │   ├── CustomCustomerDetailsService.java
│   │   │               │   ├── CustomEmailMessage.java
│   │   │               │   ├── Databaselnitializer.java
│   │   │               │   └── Rolelnitializer.java
│   │   │               │ 
│   │   │               └── LibraryManagementSystemApplication.java
│   │   │               
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   │       
│   └── test/
│   
├── mvnw  
├── mvnw.cmd  
├── pom.xml
└── README.md
```

## Dependencies
Dependencies used in the Library Management System:

### Core Spring Boot Dependencies
- **`spring-boot-starter-data-jpa`**: Spring Data JPA for ORM and database interactions.
- **`spring-boot-starter-security`**: Spring Security for authentication and authorization.
- **`spring-boot-starter-web`**: For building RESTful APIs and handling web requests.

### Development and Testing
- **`spring-boot-devtools`**: Enables automatic restarts during development.
- **`spring-boot-starter-test`**: JUnit and Mockito for testing.
- **`spring-security-test`**: Testing utilities for Spring Security.

### Database and Persistence
- **`postgresql`**: PostgreSQL driver for database connections.
- **`jakarta.validation-api`**: Bean validation for data integrity.

### JSON and Serialization
- **`spring-boot-starter-json`**: JSON serialization and deserialization.
- **`jackson-databind`**: Core Jackson library for JSON processing.

### Authentication and Security
- **`jjwt`**: JSON Web Tokens (JWT) for secure token-based authentication.

### Email Support
- **`spring-boot-starter-mail`**: For sending emails (e.g., for notifications).

### Utility Libraries
- **`lombok`**: Reduces boilerplate code (e.g., getters/setters).
- **`modelmapper`**: Simplifies mapping between DTOs and entities.

### Logging
- **`slf4j-api`**: SLF4J for logging interface.
- **`logback-classic`**: Logback as the logging implementation.

### XML Binding
- **`jaxb-api`**: XML binding support for working with XML data.

[//]: # (### Testing &#40;Extended&#41;)

[//]: # (- **`mockito-core`**: For creating mock objects in tests.)

[//]: # (- **`mockito-junit-jupiter`**: Integrates Mockito with JUnit 5.)


## Usage

1. **Authentication & User Management**: JWT-secured authentication with customizable role access.
2. **Book Management**: Admins manage the library catalog, while users can view and reserve books.
3. **Borrowing & Notifications**: Users borrow and return books, with email reminders for due dates.
4. **Community Engagement**: Users can rate and review books, improving resource selection.

## Getting Started

1. **Clone Repository**
   ```bash
   git clone https://github.com/sumanbisunkhe/library-management-system.git
   cd library-management-system
   ```

2. **Database Setup**
    - Configure PostgreSQL and update `application.properties` with database credentials.
    ```properties
    # ========== Application Name ==========
    spring.application.name=library-management-system
    
    # ========== Database Configuration ==========
    spring.datasource.url=jdbc:postgresql://localhost:5432/library_management_system_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.datasource.driver-class-name=org.postgresql.Driver
    
    # ========== Hibernate/JPA Settings ==========
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    
    # ========== JWT Configuration ==========
    jwt.secret=superSecretKeyHere
    
    # ========== Logging Configuration ==========
    logging.level.org.hibernate.SQL=DEBUG
    logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
    
    # ========== Mail Configuration ==========
    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username=your_email_address
    spring.mail.password=your_email_app_password
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    ```
3. **Run Application**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Contributors

- **[Suman Bisunkhe](https://github.com/sumanbisunkhe)**  

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.


