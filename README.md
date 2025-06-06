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
│   │   │               │   ├── PaymentRepository.java
│   │   │               │   ├── UserArticleAccessRepository.java
│   │   │               │   └── UserInfoRepository.java
│   │   │               ├── Data/
│   │   │               │   └── DataLoader.java
│   │   │               ├── Entity/
│   │   │               │   ├── Article.java
│   │   │               │   ├── AuthRequest.java
│   │   │               │   ├── Category.java
│   │   │               │   ├── Payment.java
│   │   │               │   ├── UserArticleAccess.java
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
│   │   │               │   ├── PaymentRest.java
│   │   │               │   └── UserInfoRest.java
│   │   │               ├── RestImpl/
│   │   │               │   ├── ArticleRestImpl.java
│   │   │               │   ├── CategoryRestImpl.java
│   │   │               │   ├── PaymentRestImpl.java
│   │   │               │   └── UserInfoRestImpl.java
│   │   │               ├── Service/
│   │   │               │   ├── ArticleService.java
│   │   │               │   ├── CategoryService.java
│   │   │               │   ├── PaymentService.java
│   │   │               │   ├── UserArticleAccessService.java
│   │   │               │   └── UserInfoService.java
│   │   │               ├── ServiceImpl/
│   │   │               │   ├── ArticleServiceImpl.java
│   │   │               │   ├── CategoryServiceImpl.java
│   │   │               │   ├── PaymentServiceImpl.java
│   │   │               │   ├── UserArticleAccessServiceImpl.java
│   │   │               │   └── UserInfoServiceImpl.java
│   │   │               ├── Utils/
│   │   │               │   ├── DRMUtils.java
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



## 🧩 Paid & Unpaid Article Access Flow — Download & Payment Logic Design

``` 
User Visits Article Page
│
├── Is Article `contentType == UNPAID`?
│     │
│     └── YES
│         │
│         ├── Allow Full Access: View + Screenshot + Copy + Record
│         │
│         └── Show Download Options:
│               ├── [1] Download This Article (PDF/DOCX)
│               └── [2] Download All Articles from Same Category (PDF/DOCX)
│
└── NO → Article is PAID
      │
      ├── Show Tag: ⚠ PAID CONTENT
      ├── Show Info Icon (!)
      │     └── On Click: Show Description of 2 Payment Tiers
      │            ├── ₹10 - View Only (No screenshot, record, copy, download)
      │            └── ₹50 - Full Access (View + Download + Copy + Screenshot)
      │
      └── Ask User to Make Payment
            │
            ├── ₹10 Payment Done?
            │     └── YES → Allow View Only
            │               ├── Block Screenshot
            │               ├── Block Screen Record
            │               └── Block Copy
            │
            └── ₹50 Payment Done?
                  └── YES → Allow:
                            ├── View + Screenshot + Copy + Record
                            └── Show Download Option:
                                  └── [1] Download This Article Only (PDF/DOCX)
```

### 💰 Payment Options for Paid Articles:

| Tier | Price       | Access Rights                                                                               |
| ---- | ----------- | ------------------------------------------------------------------------------------------- |
| ₹10  | View-only   | ❌ Screenshot<br>❌ Screen Record<br>❌ Copy<br>❌ Download                                     |
| ₹50  | Full access | ✅ Screenshot<br>✅ Screen Record<br>✅ Copy<br>✅ Download (PDF/DOCX only for current article) |


 **⚠️ Only ₹50 users get download access for paid articles.**



### 📥 Download Feature Logic:

| User          | Content Type | Download Options                      |
| ------------- | ------------ | ------------------------------------- |
| Any           | UNPAID       | ✅ This article<br>✅ All from category |
| ₹50 Paid User | PAID         | ✅ This article only                   |
| ₹10 Paid User | PAID         | ❌ No download                         |


### ❓ Info Icon for Payment Tiers:

An icon (!) beside paid articles explains both payment tiers.
Tooltip/dialog shows:

    ₹10: Limited view

    ₹50: Full access + download


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

## Snapshots:

<img width="1440" alt="Screenshot 2025-06-06 at 5 31 28 PM" src="https://github.com/user-attachments/assets/80edba56-8865-48d7-99d0-2657396a5345" />

<img width="1440" alt="Screenshot 2025-06-06 at 5 31 44 PM" src="https://github.com/user-attachments/assets/32b15263-b189-4ffb-80e8-7635b899d690" />

<img width="1440" alt="Screenshot 2025-06-06 at 5 32 00 PM" src="https://github.com/user-attachments/assets/694c2234-66fa-447e-b581-324ce62b0561" />

<img width="1440" alt="Screenshot 2025-06-06 at 5 32 14 PM" src="https://github.com/user-attachments/assets/de45df78-6902-40b2-9e32-77748f945a82" />

<img width="1440" alt="Screenshot 2025-06-06 at 5 32 35 PM" src="https://github.com/user-attachments/assets/945991cf-0acf-47d4-94f3-c4bae345e47d" />

<img width="1440" alt="Screenshot 2025-06-06 at 5 33 48 PM" src="https://github.com/user-attachments/assets/a7a3b059-2546-4206-970f-1c94d5937042" />

<img width="1440" alt="Screenshot 2025-06-06 at 5 34 03 PM" src="https://github.com/user-attachments/assets/c0d2dfcb-8a72-40a6-b598-fea0a5e53d24" />

<img width="1440" alt="Screenshot 2025-06-06 at 5 34 26 PM" src="https://github.com/user-attachments/assets/39e25433-bded-4b03-9c71-cf5847790d6b" />

<img width="1440" alt="Screenshot 2025-06-06 at 5 35 02 PM" src="https://github.com/user-attachments/assets/a9d63d9d-5701-4693-b899-64faf950b336" />

<img width="1440" alt="Screenshot 2025-06-06 at 5 35 23 PM" src="https://github.com/user-attachments/assets/3390a8e6-d7fd-423e-9f6a-6b132eb06a16" />


## 👨‍💻 Developed By

**[Prabhat Kumar](https://prabhatkr.vercel.app/)** [ Backend Developer | Java & Spring Enthusiast ]

🚀 Founder - **[QuantumFusion Solutions](https://quantumfusion-solutions.vercel.app/)**

 ---

### 📎 Frontend Repo
👉 https://github.com/Hugs-4-Bugs/ArticleHub-Frontend.git

(Developed in Angular & Node.js by Prabhat Kumar)

