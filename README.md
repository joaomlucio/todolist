# Project Dependencies
## Without docker:
    -Spring Boot: 3.2.4
    -JDK: 21
    -Maven: 3.9.6
    -Postgres: 14.5
## With Docker:
    -Docker: 24.0.6
    -Compose: v2.21.0

# Execution
### For the project version, to run the api you only need to run `mvn spring-boot:run` from the todolist folder. Remember, you have to set the database credentials in the application file, which you can find at <b>todolist\src\main\resources</b>
### For the project version, to run the test you only need to run `mvn clean test` from the todolist folder

### For the docker version, you simply need to run `docker-compose up -d --build` from the docker folder. Both the app and database containers are going to be created automatically and the api can already be reached at `http://localhost:8080/todo/entries`







