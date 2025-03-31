# Order Service API - Java 21, Spring Boot

This project provides APIs for user and order management. It uses Spring Boot, MongoDB for data storage, and AWS Cognito for OAuth2-based authentication.

## Features

- **User API**:
    - **Sign Up**: Register a new user in Cognito.
    - **Login**: Authenticate a user using OAuth2 and retrieve tokens.

- **Order API**:
    - **Create Order**: Create a new order for an authenticated user.
    - **Get Customer Orders**: Retrieve all orders for the authenticated customer.
    - **Get Order Details**: Get the details of a specific order.
    - **Delete Order**: Delete an order for an authenticated user.

- **MongoDB**: Persists data regarding orders.

- **Swagger Documentation**: Interactive API documentation via Swagger UI.

## Prerequisites

Before running the application, make sure you have the following:

1. **Java 21**: Required to run the application. [Download Java](https://openjdk.java.net/)
2. **Docker**: Required to set up and run MongoDB locally. [Download Docker](https://www.docker.com/get-started)
3. **Maven**: Required to build and run the application. [Download Maven](https://maven.apache.org/download.cgi)
4. **AWS Cognito**: Set up your own AWS Cognito credentials and update the properties accordingly.

## Running MongoDB with Docker

To run MongoDB locally, follow these steps:

1. **Install Docker** if you haven't already.
2. Open a terminal and run the following command to pull the official MongoDB Docker image:

   ```bash
   docker pull mongo:latest
   ```

3. Run the MongoDB container with:

   ```bash
   docker run -d -p 27017:27017 --name mongodb mongo:latest
   ```

   MongoDB will be available at `localhost:27017`.

## Application Setup

1. **Clone the Repository**:

   ```bash
   git clone https://your-repository-url.git
   cd your-repository-directory
   ```

2. **Build the Application**:

   ```bash
   mvn clean install
   ```

3. **Run the Application**:

   ```bash
   mvn spring-boot:run
   ```

   The application will be running on `http://localhost:8080`.

## Configuration

The application requires an `application.properties` file for configuration. Students must create this file in `src/main/resources/` and populate it with the necessary details.

### MongoDB Configuration

Create an `application.properties` file and add the following:

```properties
spring.data.mongodb.uri=mongodb://root:NixJavaCourse2025@localhost:27017/orders_db
spring.data.mongodb.database=orders_db
```

If you want to use a different MongoDB instance, replace the URI and database name with your own values.

### AWS Cognito OAuth2 Configuration

Add the following to `application.properties` and replace the placeholders with your AWS Cognito details:

```properties
# AWS Cognito OAuth2 Configuration
spring.security.oauth2.client.provider.app1-provider.issuer-uri=
spring.security.oauth2.client.registration.app1.provider=
spring.security.oauth2.client.registration.app1.client-id=
spring.security.oauth2.client.registration.app1.client-secret=
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=
```

These are required for configuring the OAuth2 client and JWT resource server for authentication. Be sure to replace the `client-id`, `client-secret`, and `issuer-uri` with your own values from AWS Cognito.

### Other Important Configuration

```properties
# AWS Credentials - Replace these with your own keys for access to sdk
aws.credentials.access-key=
aws.credentials.secret-key=
aws.cognito.region.static=
```

Ensure that the `application.properties` file is correctly configured before running the application.