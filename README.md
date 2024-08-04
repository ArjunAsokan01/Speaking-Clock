# Speaking Clock

## Project Description

This project is a Spring Boot application that converts the current time or a given time into words.

## Prerequisites

- JDK 8 or above
- Maven

## Build and Run

1. Clone the repository:
    ```
    git clone <repository_url>
    ```

2. Navigate to the project directory:
    ```
    cd speaking-clock
    ```

3. Build the project:
    ```
    mvn clean install
    ```

4. Run the application:
    ```
    mvn spring-boot:run
    ```

## API Endpoints

- `GET /time/current` - Returns the current time in words.
- `GET /time/{time}` - Returns the given time in words.

## Swagger

Swagger UI is available at `/swagger-ui.html`.
