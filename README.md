# 📝 Blog System — Spring Security (Lab 12)

A secure RESTful **Blog System** built with **Spring Boot** and **Spring Security**.
Users can register, log in with **HTTP Basic Authentication**, and manage their own blogs, while **admins** can view all users and all blogs.

> Tuwaiq Academy — Lab 12

---

## 🚀 Tech Stack

| Technology | Usage |
|---|---|
| Java 17+ | Language |
| Spring Boot | Application framework |
| Spring Security | Authentication & authorization |
| Spring Data JPA | Database access |
| MySQL | Database |
| Lombok | Boilerplate reduction |
| Jakarta Validation | Input validation |
| BCrypt | Password hashing |

---

## ✨ Features

- 🔐 User registration with **BCrypt** password hashing
- 🔑 Login using **HTTP Basic Auth** and logout endpoint
- 👥 Role-based access control (`USER` / `ADMIN`)
- 📝 Full CRUD for blogs
- 🛡️ Ownership checks: users can only view, update, or delete **their own** blogs
- 🔎 Get blog by **ID** or by **title**
- ✅ Validation on all inputs with clear error messages
- ⚠️ Centralized exception handling with `@RestControllerAdvice`

---

## 🗂️ Project Structure

```
src/main/java/com/example/springsecurityexercise
├── Advice
│   └── ControllerAdvice.java
├── Api
│   ├── ApiException.java
│   └── ApiResponse.java
├── Config
│   └── Configuration.java
├── Controller
│   ├── AuthController.java
│   └── BlogController.java
├── Entity
│   ├── Blog.java
│   └── User.java
├── Repository
│   ├── AuthRepository.java
│   └── BlogRepository.java
└── Service
    ├── AuthService.java
    ├── BlogService.java
    └── MyUserDetailsService.java
```

---

## 🧩 Entities & Relationship

```
User (1) ──────────── (*) Blog
```

### User
| Field | Type | Validation |
|---|---|---|
| id | Long | Auto generated |
| username | String | Not empty, 4–20 characters, letters/numbers/_ only, unique |
| password | String | Not empty, at least 6 characters (stored as BCrypt hash) |
| role | String | Set automatically to `USER` on register |

### Blog
| Field | Type | Validation |
|---|---|---|
| id | Long | Auto generated |
| title | String | Not empty, 3–50 characters, unique per user |
| body | String | Not empty, 10–1000 characters |
| user | User | Set automatically from the logged-in user |

---

## 🔐 Security Rules

| Endpoint | Access |
|---|---|
| `POST /api/v1/auth/register` | 🌍 Public |
| `GET /api/v1/auth/get` | 👑 ADMIN only |
| `GET /api/v1/blog/get` | 👑 ADMIN only |
| `POST /api/v1/auth/logout` | 🔑 Authenticated |
| Any other endpoint | 🔑 Authenticated |

Authentication uses **HTTP Basic Auth**: send the username and password with each request.

---

## 📡 API Endpoints

Base URL: `http://localhost:8080`

### Auth — `/api/v1/auth`

| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/register` | Register a new user | Public |
| GET | `/get` | Get all users | ADMIN |
| POST | `/logout` | Logout | Authenticated |

### Blog — `/api/v1/blog`

| Method | Endpoint | Description | Access |
|---|---|---|---|
| GET | `/get` | Get all blogs | ADMIN |
| GET | `/get/my/blogs` | Get all blogs of the logged-in user | Owner |
| GET | `/get/{blogId}` | Get blog by ID | Owner |
| GET | `/get/title/{title}` | Get blog by title | Owner |
| POST | `/add` | Add a new blog | Authenticated |
| PUT | `/update/{blogId}` | Update a blog | Owner |
| DELETE | `/delete/{blogId}` | Delete a blog | Owner |

---

## 📦 Request Examples

### Register
```http
POST /api/v1/auth/register
Content-Type: application/json
```
```json
{
  "username": "ali",
  "password": "123456"
}
```

### Add / Update Blog
```http
POST /api/v1/blog/add
Authorization: Basic (ali / 123456)
Content-Type: application/json
```
```json
{
  "title": "My first blog",
  "body": "This is my first blog post"
}
```

### Response format
```json
{
  "message": "Blog added successfully"
}
```

---

## ⚠️ Error Handling

All errors return a consistent JSON response:

```json
{
  "message": "Blog not found"
}
```

| Status | Meaning |
|---|---|
| `200` | Success |
| `400` | Validation error or business rule violation |
| `401` | Not logged in or wrong credentials |
| `403` | Logged in but not allowed (e.g. USER calling an ADMIN endpoint) |

---

## ▶️ How to Run

1. Clone the repository.
2. Create a MySQL database and update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/blog_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. The API runs at `http://localhost:8080`.

---

## 👑 Creating an Admin

Registration always creates a `USER`. To create an admin:

1. Register a user normally (e.g. `admin`).
2. Update the role in the database:
   ```sql
   UPDATE `user` SET role = 'ADMIN' WHERE username = 'admin';
   ```

---

## 👤 Author

**Your Name** — Tuwaiq Academy
