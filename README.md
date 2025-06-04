# 📰 ArticleHub Backend (Spring Boot)

This is the **backend service** for the ArticleHub platform, developed using Spring Boot. It provides REST APIs to manage articles, categories, and users with authentication support via JWT. The frontend (coming soon) will consume these APIs to create a complete publishing platform.

---

## 🔧 Tech Stack

* **Java 17**
* **Spring Boot 3**
* **Spring Security (JWT)**
* **Spring Data JPA**
* **Lombok**
* **Maven**
* **MySQL / H2**
* **Postman** (for API testing)

---

## 📁 Project Structure

```
hugs-4-bugs-articlehub-application/
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── article/
│   │   │           └── hub/
│   │   │               ├── Application.java
│   │   │               ├── Config/
│   │   │               │   └── SecurityConfig.java
│   │   │               ├── DAO/
│   │   │               │   ├── ArticleRepository.java
│   │   │               │   ├── CategoryRepository.java
│   │   │               │   └── UserInfoRepository.java
│   │   │               ├── Data/
│   │   │               │   └── DataLoader.java
│   │   │               ├── Entity/
│   │   │               │   ├── Article.java
│   │   │               │   ├── AuthRequest.java
│   │   │               │   ├── Category.java
│   │   │               │   └── UserInfo.java
│   │   │               ├── Filter/
│   │   │               │   └── JwtAuthFilter.java
│   │   │               ├── JWT/
│   │   │               │   ├── JwtService.java
│   │   │               │   ├── UserInfoDetails.java
│   │   │               │   └── UserInfoService.java
│   │   │               ├── Rest/
│   │   │               │   ├── ArticleRest.java
│   │   │               │   ├── CategoryRest.java
│   │   │               │   └── UserInfoRest.java
│   │   │               ├── RestImpl/
│   │   │               │   ├── ArticleRestImpl.java
│   │   │               │   ├── CategoryRestImpl.java
│   │   │               │   └── UserInfoRestImpl.java
│   │   │               ├── Service/
│   │   │               │   ├── ArticleService.java
│   │   │               │   ├── CategoryService.java
│   │   │               │   └── UserInfoService.java
│   │   │               └── ServiceImpl/
│   │   │                   ├── ArticleServiceImpl.java
│   │   │                   ├── CategoryServiceImpl.java
│   │   │                   └── UserInfoServiceImpl.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── article/
│                   └── hub/
│                       └── ApplicationTests.java
└── .mvn/
    └── wrapper/
        └── maven-wrapper.properties

```

---

## ✅ Features

* 🔐 **JWT Authentication** (Login & Protected routes)
* 📝 Add / Update / Delete Articles
* 📚 Filter by **published status**
* 📦 Categorize articles by ID or name
* 🧑 Manage users with secure passwords (BCrypt)
* 🌐 RESTful API architecture

---

## 📦 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/hugs-4-bugs-articlehub-application.git
cd hugs-4-bugs-articlehub-application
```

### 2. Set Up Database

Edit `src/main/resources/application.properties`:

For MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/articlehub
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

For H2 (in-memory), use:

```properties
spring.datasource.url=jdbc:h2:mem:articlehub
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

### 3. Build and Run

#### Using Maven:

```bash
./mvnw spring-boot:run
```

Or from your IDE, run `Application.java`.

---

## 🔐 Authentication (JWT)

* Use `/user/authenticate` to log in.
* It returns a **JWT token** to be used in subsequent requests as a Bearer token in the `Authorization` header.
* Protected routes (like getAllArticles) require this token.

---

## 🔍 API Endpoints

### 📄 Article APIs

| Method | Endpoint                          | Description                      |
| ------ | --------------------------------- | -------------------------------- |
| POST   | `/article/addNewArticle`          | Add a new article                |
| POST   | `/article/updateArticle`          | Update an existing article       |
| GET    | `/article/getAllArticle`          | View all articles (admin only)   |
| GET    | `/article/getAllPublishedArticle` | View published articles (public) |
| DELETE | `/article/deleteArticle/{id}`     | Delete article by ID             |

### 🧾 Category APIs

| Method | Endpoint           | Description        |
| ------ | ------------------ | ------------------ |
| GET    | `/category/getAll` | Get all categories |
| POST   | `/category/add`    | Add a new category |

### 👤 User APIs

| Method | Endpoint             | Description         |
| ------ | -------------------- | ------------------- |
| POST   | `/user/signup`       | Register a user     |
| POST   | `/user/authenticate` | Login and get token |

---

## 🔬 Sample Request

### Add New Article (POST `/article/addNewArticle`)

```json
{
  "title": "Intro to Spring",
  "content": "Spring Boot makes development easy!",
  "categoryId": 1,
  "status": "Published"
}
```

---

## 🧪 Testing

Use **Postman** or similar tools to:

* Register & login a user
* Add Authorization: `Bearer <token>` in headers
* Test protected APIs

---

## 📦 Future Enhancements

* 🔄 Pagination and search
* 📝 Rich text content support
* 📊 Analytics for views
* 🌍 Full React frontend integration

---

## 👨‍💻 Developed By

**[Prabhat Kumar](https://prabhatkr.vercel.app/)**  Backend Developer | Java & Spring Enthusiast

🚀 Founder - **[QuantumFusion Solutions](https://quantumfusion-solutions.vercel.app/)**

 ---

### 📎 Frontend Repo
👉 https://github.com/Hugs-4-Bugs/ArticleHub-Frontend.git

(Developed in Spring Boot by Prabhat Kumar)

