# Quiz App

A simple Quiz Application built with **Spring Boot**, featuring authentication, quizzes, questions, and user attempts.

---

## Features

* User registration and login (JWT-based)
* Role-based access (Admin/Instructors vs Users)
* Create, read, update, delete quizzes
* Add, edit, delete questions
* Track quiz attempts and scores

---

## Tech Stack

* Java 17+, Spring Boot
* Spring Security + JWT
* MySQL
* Maven

---

## Getting Started

1. Clone the repo:

```bash
git clone https://github.com/Basharkhan/quiz
cd quiz-app
```

2. Configure database in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quizdb?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

3. Build and run:

```bash
mvn clean install
mvn spring-boot:run
```

App runs at `http://localhost:8080`.

---

## API Endpoints

### Auth
* `POST /api/auth/admin` – Admin
* `POST /api/auth/teacher` – Teacher
* `POST /api/auth/student` – Student
* `POST /api/auth/login` – Login (returns JWT token)

### Quiz
* `POST /api/quizzes` – Create quiz (Admin and Teacher)
* `GET /api/quizzes/{id}` – Quiz details
* `GET /api/quizzes/{id}/attempts` – Quiz details for students
* `PUT /api/quizzes/{id}` – Update quiz (Admin and Teacher)
* `GET /api/quizzes` – List quizzes
* `DELETE /api/quizzes/{id}` – Delete Quiz (Admin and Teacher)

### Question
* `POST /api/quizzes/{id}/questions` – Add question (Admin and Teacher)
* `PUT /api/questions/{id}` – Update question (Admin and Teacher)
* `GET /api/questions/{id}/{quizId}` – Questions by quiz
* `GET /api/questions/{id}` – Question details
* `DELETE /api/questions/{id}` – Delete question (Admin and Teacher)

### Attempt
* `POST /api/attempts` – Submit attempt
* `GET /api/attemps/user/{userId}` – Get user attempt details

---

## Example: Login & Use Token

**Login Request**:

```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

**Login Response**:

```json
{
  "status": 200,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJSUzUxMiJ9.eyJzdWIiOiJqb2huQGV4YW1wbGUuY29tIiwicm9sZSI6IkFETUlOIiwiaWF0IjoxNzU5OTA5NTUxLCJleHAiOjE3NTk5MTMxNTF9.qAj-k8rVl31wd7NnOw_5FQgMhqensT48HuIc2PSHazgnNryIK18nOI2ed7jWyYoislXSORiWYMIXZEi9XmJ6x5tP2yn4D8PfJO_WOTSKe0FGem8Dj5ezX5PKtaLrD1mSVfGaK6e-L9YHFiwwNHllwIEXQCH6TDotSbpSA7kjiC39_2XFcDR6JU68Tj72SVUup3IqtNS4b6nA5QbWaEm_vagZB1Bm52re9cGvDDEVeBVBtwEq7pqKooTbZ1rxmRJo50_n7xCzfNEp2wwqKYgcdspRBr-NNcYKDGkmtSh0zw0S8Uugwy5K4WX0iht-Y1moi9nZ1G1sEeLkXVkfMW5nMw",
    "userDetailsDto": {
      "email": "john@example.com",
      "fullName": "John Doe",
      "role": "ADMIN"
    }
  },
  "timestamp": "2025-10-08T13:45:51.539438979"
}
```

> Use token for secured endpoints:
> `Authorization: Bearer <token>`

---

## Database

* **User**: id, fullName, email, password, role, created_at
* **Quiz**: id, title, description, createdBy, questions
* **Question**: id, text, quiz, options
* **Option**: id, text, correct, question
* **Attempt**: id, student, quiz, score, attemptedDate

---