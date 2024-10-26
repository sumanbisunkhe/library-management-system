# 📚 Library Management System

Welcome to the **Library Management System**! This demo Spring Boot application serves as a comprehensive solution for managing library resources, users, and transactions. It empowers libraries to handle user registrations, book reservations, borrowing, notifications, fines, and reviews seamlessly through secure RESTful APIs. Leveraging JWT-based authentication and fine-grained role-based authorization, this application ensures a robust security model for both end-users and administrators.

## 🌟 Key Features

- **🔐 Secure Authentication & Authorization**: JWT-based token authentication with role-based access.
- **📚 Book Management**: Add, update, and search for books, complete with real-time availability tracking.
- **📥 Borrowing & Returns**: Streamlined borrowing and returning processes, with d fine calculations.
- **📬 Notifications**: Timely email reminders for reservations, due dates, and fines.
- **⭐ User Reviews**: Engage the community by allowing users to rate and review books.
- **⚠️ Detailed Error Responses**: JSON responses for error handling, simplifying front-end integration.

## 💻 Technology Stack

| **Category**               | **Technologies**                             |
|----------------------------|----------------------------------------------|
| **Framework**              | Spring Boot                                  |
| **Security**               | Spring Security, JWT                         |
| **Database**               | PostgreSQL, Spring Data JPA                 |
| **Utilities**              | Lombok, ModelMapper, Jakarta Validation API  |
| **Email Support**          | Spring Boot Starter Mail                     |
| **JSON Handling**          | Jackson Databind                             |

## 🗂️ Project Structure

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
├── LICENSE  
├── mvnw  
├── mvnw.cmd  
├── pom.xml
└── README.md
```


## 🔗 Dependencies

### Core Spring Boot Dependencies
- **`spring-boot-starter-data-jpa`**: For ORM and database interactions.
- **`spring-boot-starter-security`**: Ensures robust authentication and authorization.
- **`spring-boot-starter-web`**: Facilitates building RESTful APIs and handling web requests.

### Development and Testing
- **`spring-boot-devtools`**: Automatic restarts during development.
- **`spring-boot-starter-test`**: JUnit and Mockito for effective testing.
- **`spring-security-test`**: Utilities for testing Spring Security.

### Database and Persistence
- **`postgresql`**: Driver for PostgreSQL database connections.
- **`jakarta.validation-api`**: Ensures data integrity with bean validation.

### JSON and Serialization
- **`spring-boot-starter-json`**: For JSON serialization and deserialization.
- **`jackson-databind`**: Core library for JSON processing.

### Authentication and Security
- **`jjwt`**: For secure token-based authentication using JSON Web Tokens (JWT).

### Email Support
- **`spring-boot-starter-mail`**: Facilitates email notifications.

### Utility Libraries
- **`lombok`**: Reduces boilerplate code (e.g., getters/setters).
- **`modelmapper`**: Simplifies mapping between DTOs and entities.

### Logging
- **`slf4j-api`**: Logging interface.
- **`logback-classic`**: Implementation for logging.

### XML Binding
- **`jaxb-api`**: Support for XML binding.

## 🚀 Usage

1. **Authentication & User Management**: Secure JWT authentication with customizable role access.
2. **Book Management**: Admins oversee the library catalog; users can explore and reserve books.
3. **Borrowing & Notifications**: Users can borrow and return books with email reminders for due dates.
4. **Community Engagement**: Foster resource selection by allowing users to rate and review books.

## ⚙️ Getting Started

1. **Clone the Repository**
   ```bash
   git clone https://github.com/sumanbisunkhe/library-management-system.git
   cd library-management-system
   ```

2. **Database Setup**
   - Configure PostgreSQL and update `application.properties` with your database credentials.
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

## 👑 Contributors

🌟 **[Suman Bisunkhe](https://github.com/sumanbisunkhe)** ✨ **Developer Extraordinaire** | Shaping the future with code and creativity. 🚀

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.



