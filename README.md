Employee Management System

The Employee Management System is a REST API that allows you to manage employees of a company. It exposes endpoints for creating, retrieving, updating, and deleting employees. The application is built using Spring Boot, and it includes Swagger for API documentation, Kafka for event publishing, and Docker for containerizing the database.
Prerequisites

Before running the application, make sure you have the following software installed on your machine:

    Java JDK 8 or higher
    Docker (if you want to run the database in a Docker container)
    Kafka (if you want to use Kafka as the message broker for event publishing)

Setup

    Clone the repository from GitHub:

  

git clone <repository-url>

Navigate to the project directory:



cd employee-management-system

Build the application using Maven:

mvn clean install

Start the application using Maven:



mvn spring-boot:run

This will start the application on the default port 8080.

Access the Swagger UI for API documentation:

Open a web browser and go to http://localhost:8080/swagger-ui.html to access the Swagger UI, where you can view and test the available API endpoints.

(Optional) Run the database in a Docker container:

If you want to run the database in a Docker container, you can use the following steps:

    Install Docker on your machine if you haven't already done so.

    Pull the MySQL Docker image:

docker pull mysql

Start a MySQL container:



        docker run -d -p 3306:3306 --name employee-db -e MYSQL_ROOT_PASSWORD=<root-password> -e MYSQL_DATABASE=employee_db mysql

        Replace <root-password> with your desired root password for the MySQL container.

        Update the application.properties file with the correct database connection details, including the URL, username, password, and database name.

API Endpoints

The Employee Management System exposes the following API endpoints:

    POST /employees: Create a new employee with the specified details. Requires a request body in JSON format with the employee details.

    GET /employees: Get a list of all employees. Returns a JSON array of employee objects.

    GET /employees/{id}: Get an employee by UUID. Requires the UUID of the employee as a path variable. Returns a JSON object with the employee details.

    PUT employees/{id}: Update an employee by UUID. Requires the UUID of the employee as a path variable, and a request body in JSON format with the updated employee details.

    DELETE employees/{id}: Delete an employee by UUID. Requires the UUID of the employee as a path variable.

Authentication

Authentication is required to access the POST, PUT, and DELETE endpoints for creating, updating, and deleting employees. The application uses Spring Security for authentication. By default, the application uses basic authentication with a pre-configured username and password. You can update the security settings in the application.properties file to configure your own authentication mechanism, such as OAuth or JWT.
Testing

The application includes unit and integration tests using JUnit and Mockito for testing the functionality of the endpoints and service methods. You can run the tests using Maven:



mvn test

Event Publishing with Kafka

The application publishes events related to employee creation, update, and deletion to a Kafka
