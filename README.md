# Todo Microservices

A microservices-based Todo List application built with Java, Spring Boot, and React.

## Architecture

The application consists of the following microservices:

- **Eureka Server**: Service discovery server
- **API Gateway**: Routes requests to appropriate microservices
- **User Service**: Handles user registration, authentication, and management
- **Todo Service**: Manages todo items (CRUD operations)
- **Frontend**: React-based web application

## Technology Stack

- **Backend**:
  - Java 17
  - Spring Boot 3.2.0
  - Spring Cloud 2023.0.0
  - Spring Security with JWT
  - H2 Database (in-memory database for development)
  - Maven
  - OpenFeign for inter-service communication

- **Frontend**:
  - React 18
  - React Router for navigation
  - Axios for HTTP requests
  - CSS for styling

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Node.js 14 or higher
- npm or yarn

### Building the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/sloumver/todo-microservices.git
   cd todo-microservices
   ```

2. Build all backend services:
   ```bash
   mvn clean install
   ```

3. Install frontend dependencies:
   ```bash
   cd frontend
   npm install
   cd ..
   ```

### Running the Application

Start the services in the following order:

1. **Eureka Server** (Service Discovery):
   ```bash
   cd eureka-server
   mvn spring-boot:run
   ```
   Access at: http://localhost:8761

2. **User Service**:
   ```bash
   cd user-service
   mvn spring-boot:run
   ```
   Runs on port 8081

3. **Todo Service**:
   ```bash
   cd todo-service
   mvn spring-boot:run
   ```
   Runs on port 8082

4. **API Gateway**:
   ```bash
   cd api-gateway
   mvn spring-boot:run
   ```
   Runs on port 8080

5. **Frontend**:
   ```bash
   cd frontend
   npm start
   ```
   Access at: http://localhost:3000

## API Endpoints

### User Service (via API Gateway)

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and receive JWT token
- `GET /api/auth/validate?token={token}` - Validate JWT token
- `GET /api/users/{username}` - Get user by username

### Todo Service (via API Gateway)

All endpoints require:
- `Authorization: Bearer {token}` header
- `X-User-Id: {userId}` header

- `POST /api/todos` - Create a new todo
- `GET /api/todos` - Get all todos for the user (with pagination)
- `GET /api/todos/{id}` - Get a specific todo
- `PUT /api/todos/{id}` - Update a todo
- `DELETE /api/todos/{id}` - Delete a todo

## Database Access

Both User Service and Todo Service use H2 in-memory databases. You can access the H2 console:

- User Service: http://localhost:8081/h2-console
- Todo Service: http://localhost:8082/h2-console

Default credentials:
- JDBC URL: `jdbc:h2:mem:userdb` (or `jdbc:h2:mem:tododb`)
- Username: `sa`
- Password: (empty)

## Features

- Microservices architecture with service discovery
- JWT-based authentication
- RESTful APIs
- User registration and login
- Todo CRUD operations
- User-specific todo management
- Pagination support for todo listings
- Responsive web interface
- Real-time service registration with Eureka
- API Gateway for unified access point
- Inter-service communication using Feign clients

## Security

- Passwords are encrypted using BCrypt
- JWT tokens for stateless authentication
- User isolation - users can only access their own todos
- CORS configuration for frontend access

## Development

### Adding a New Microservice

1. Create a new Maven module in the project
2. Add Spring Boot and Spring Cloud dependencies
3. Configure the service to register with Eureka
4. Add routing rules in the API Gateway
5. Implement your business logic

### Testing

You can test the APIs using tools like Postman or curl:

```bash
# Register a user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# Create a todo (replace TOKEN and USER_ID)
curl -X POST http://localhost:8080/api/todos \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "X-User-Id: 1" \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Todo","description":"This is a test","status":"PENDING"}'
```

## Future Enhancements

- Add PostgreSQL/MySQL for production
- Implement Docker containerization
- Add comprehensive unit and integration tests
- Implement circuit breakers with Hystrix
- Add centralized configuration with Spring Cloud Config
- Implement distributed tracing with Zipkin
- Add API documentation with Swagger
- Implement caching with Redis
- Add message queuing with RabbitMQ/Kafka

## Contributing

Feel free to submit issues and pull requests. Make sure to follow the existing code style and add tests for new features.

## License

This project is open source and available under the MIT License.