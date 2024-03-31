# Chat Server Application

This is a chat server application built with Java and Spring Boot. It provides REST APIs for sending and retrieving messages in a chat room, as well as user authentication and authorization.

## Features

- User authentication with basic username/password login
- Creation of a single chat room upon server startup
- Persistent storage of chat messages in a database
- Sending and receiving messages in the chat room
- Deletion of messages by clients
- RESTful endpoints for message sending, retrieval, and deletion
- Input validation and error handling
- Swagger documentation for API endpoints
- Unit tests for service and controller layers

## Technologies Used

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- H2 Database
- Swagger (OpenAPI)
- JUnit and Mockito

## Prerequisites

- Java Development Kit (JDK) 8 or above
- Apache Maven
- IDE of your choice (IntelliJ IDEA, Eclipse, etc.)

## Getting Started

1. Clone the repository:
2. Navigate to the project directory:
3. Build the project using Maven:

5. The application will start running on `http://localhost:9083`.

## API Documentation

The API documentation is available using Swagger. Once the application is running, you can access the Swagger UI at `http://localhost:9083/swagger-ui.html`. It provides detailed information about the available endpoints, request/response models, and allows you to test the APIs.

## Configuration

The application uses an in-memory H2 database by default. If you want to use a different database, you can modify the `application.properties` file and provide the necessary database configuration.

## Testing

The project includes unit tests for the service and controller layers. To run the tests, use the following command: mvn test
