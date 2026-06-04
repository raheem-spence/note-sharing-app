# Notes App API

A backend REST API for a simple note-taking application built with Spring Boot. The system uses HTTP session-based authentication and supports CRUD operations for user-owned notes.

---

## Features

- User signup and login
- HTTP session-based authentication
- Create, read, and delete notes
- Notes are scoped to the logged-in user
- DTO-based responses for clean JSON output
- Ownership validation (users can only access their own notes)

---

## Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security (configured for session handling)
- PostgreSQL
- Hibernate

---

## Authentication

This project uses **HTTP session-based authentication**.

- On login, the user ID is stored in the session
- Each request retrieves the user ID from the session
- No JWT or token-based authentication is used in this version

---

## API Endpoints

### Auth

- `POST /auth/signup` → Create a new user
- `POST /auth/login` → Log in and create a session

### Notes

- `GET /notes` → Get all notes for the logged-in user
- `POST /notes/create` → Create a new note
- `DELETE /notes/{noteId}` → Delete a note (only if owner)

## Sample API Requests

Below are real examples of how to interact with the API.

All requests except signup and login assume the user is already logged in and a session cookie is present.

---

### 1. Register User

```http
POST /auth/signup
Content-Type: application/json

{
  "username": "raheem",
  "email": "test@email.com",
  "password": "password123"
}
```
### 2. Login User

```http
POST /auth/login
Content-Type: application/json

{
  "email": "test@email.com",
  "password": "password123"
}
```
### 3. Get Current User Notes

```http
GET /notes
```
RESPONSE
```http
[
  {
    "id": 1,
    "title": "First note",
    "content": "Testing note retrieval",
    "createdAt": "2026-06-03T22:55:13Z",
    "ownerId": 1,
    "ownerUsername": "raheem"
  }
]
```
### 4. Create Note

```http
POST /notes/create
Content-Type: application/json

{
  "title": "My New Note",
  "content": "This is a test note"
}
```
RESPONSE
```http
{
  "id": 2,
  "title": "My New Note",
  "content": "This is a test note",
  "createdAt": "2026-06-03T23:10:00Z",
  "ownerId": 1,
  "ownerUsername": "raheem"
}
```
### 5. Delete Note

```http
DELETE /notes/{noteId}
```
EXAMPLE
```http
DELETE /notes/2
```
RESPONSE
```
204 No Content
```

---

## Data Model

### User
- id
- username
- email
- passwordHash
- createdAt

### Note
- id
- title
- content
- createdAt
- updatedAt
- owner (User)

---

## Security Rules

- Users can only access their own notes
- Unauthorized or invalid actions return:
    - 403 Forbidden (not allowed)
    - 404 Not Found (missing resources)

---

## Project Status

Core backend functionality complete:

- Authentication system working with sessions
- Notes CRUD implemented (Create, Read, Delete)
- DTO layer added for clean API responses
- Ownership-based access control enforced

---

## Next Steps

- Add update (edit) note functionality
- Improve API response consistency (ResponseEntity)
- Frontend integration
