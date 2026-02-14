# ParkEase 🚗🅿️

ParkEase 2.0 is a Spring Boot–based parking lot management system built with **Java 21**, **Spring Boot**, **MySQL**, **JWT security**, and **email support**.

This project is open for contributions. DTOs and base structure are already added — contributors can help implement features and improvements.

---

## Tech Stack

- Java 21
- Spring Boot 3.5.9
- Spring Data JPA
- Spring Security + JWT
- MySQL
- Lombok
- Maven
- IntelliJ IDEA (recommended)

---

## Prerequisites

Make sure you have the following installed:

- **Java 21**
- **Maven**
- **MySQL Server**
- **IntelliJ IDEA**
- **Lombok Plugin (required)**

### Lombok Plugin (Important)
In IntelliJ IDEA:
1. Go to **Settings → Plugins**
2. Search for **Lombok**
3. Install and restart IntelliJ
4. Enable annotation processing:
    - **Settings → Build, Execution, Deployment → Compiler → Annotation Processors**
    - Check **Enable annotation processing**

---

## Database Setup (MySQL)

1. Start your MySQL server
2. Default database will be auto-created:

3. Update your MySQL credentials using environment variables (recommended)

---

## application.properties Configuration

```properties
spring.application.name=ParkEase2.0

app.admin.email=${ADMIN_EMAIL}
app.admin.password=${ADMIN_PASS}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.datasource.url=jdbc:mysql://localhost:3306/parkease02?createDatabaseIfNotExist=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}

# File Upload
file.upload.base-path=${PATH}
storage.type=local

# JWT
jwt.secret=${JWT_SECRET}

# Mail Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${EMAIL}
spring.mail.password=${PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
```
### File Upload Path Configuration 📁

The application supports local file storage.

Create a folder anywhere on your system
Example:

```C:/parkease/uploads```


or (Linux/Mac):

/home/user/parkease/uploads


Set the folder path as an environment variable:

```PATH=C:/parkease/uploads```


The application will save uploaded images/files in this folder.

Running the Application
mvn clean install
mvn spring-boot:run


The app will start on:

```http://localhost:8080```

### Git Instructions (For Contributors)
Clone the Repository
```git clone <repository-url>```
cd ParkEase2.0```

Create a New Branch
```git checkout -b your-branch-name```


#### Example:

```git checkout -b feature/admin-dashboard```

Push Your Branch
```git add .```
```git commit -m "feat: implement admin dashboard service"```
git push origin your-branch-name
