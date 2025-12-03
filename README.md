RBAC User Management Service

This is a backend service for managing users with Role-Based Access Control (RBAC), built using Spring Boot, MySQL, Kafka, and JWT authentication.

## Setup Instructions

### Prerequisites

- *Java 17* or above
- *Docker* and *Docker Compose* (for Docker-based setup)

### Local Setup

1. *Clone the repository:*
   ```bash
   git clone https://github.com/deepv24/Rbac-User-Service-Backend-Assignment.git
   cd Rbac-User-Service-Backend-Assignment

2. Install dependencies and run the application:

Using Maven:

./mvnw spring-boot:run

Using Docker Compose:

docker-compose up --build




Docker Setup

1. Ensure Docker and Docker Compose are installed on your machine.


2. Run the following command in the root of your project directory:

docker-compose up --build

This will start the application along with all necessary services, including MySQL and Kafka.


3. The application will be available at http://localhost:8080.



Database Setup

The MySQL container will automatically create the rbac_db database with the necessary tables upon application startup.

API Endpoints

User Registration: POST /api/users/register

User Login: POST /api/users/login

Health Check: GET /actuator/health

Kafka Consumer and Producer: Kafka is integrated for message processing.


JWT Authentication

The application uses JWT for user authentication.

Tokens are issued upon successful login and must be included in the Authorization header for all protected routes.

Example of how to use JWT in the Authorization header:

Authorization: Bearer <JWT_TOKEN>


Kafka Integration

The application integrates with Kafka for message processing.

It consumes and produces messages for user-related events.


Design Decisions and Assumptions

Spring Security is used to secure the endpoints with JWT authentication.

The system is designed to allow user registration, login, and role-based access.

MySQL is used as the database for user data storage.

Kafka is used for messaging between different components of the application.

The docker-compose.yml file handles the setup for all services, including MySQL and Kafka.

The service can be started using both local Maven and Docker setups.


Future Considerations

Swagger has been removed from the application as it was not functioning properly during setup.

Additional functionality like user role management and detailed logging can be added in future releases.


Troubleshooting

Connection Refused Error: Ensure that your services are up and running by checking the logs of the respective containers:

docker logs <container-name>

Example:

docker logs mysql
  




