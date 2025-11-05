# ESP32-Cam Media Server

## Description
REST API that receives photos from an ESP32-CAM, stores them in MySQL, and exposes a React dashboard for viewing, editing and deleting images.

## Class Diagram

## Setup
1. Clone the repo  
2. Create MySQL schema `esp32cam` (see `schema.sql`)  
3. `./mvnw spring-boot:run` (port 8080)  
4. `cd frontend && npm install && npm run dev` (port 5173)

## Technologies
- Java 17 & Spring Boot 3
- Spring Security + JWT
- JPA / Hibernate
- MySQL 8
- React 18 + Vite
- Axios

## API Routes
| Method | Endpoint           | Description               | Auth  |
|--------|--------------------|---------------------------|-------|
| POST   | `/auth/register`   | Create user               | no    |
| POST   | `/auth/login`      | JWT login                 | no    |
| GET    | `/photos`          | List all photos           | yes   |
| PUT    | `/photos/{id}`     | Update description        | yes   |
| DELETE | `/photos/{id}`     | Delete photo              | yes   |
| POST   | `/upload`          | ESP32 binary upload       | no    |

## Trello Board


## Presentation Slides


## Future Work
- Video streaming support  
- OAuth2 Google login  
- Kubernetes deployment

## Team
Java backend IRONHACK

## License
MIT