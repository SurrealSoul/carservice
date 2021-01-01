# Car Service Center

## Overview
This application is used for creating simple appointments for a car service center. 

Maven is required for this project, `brew install maven` in order to run this project you can use Intellij's spring boot configuration, or through the command line via `mvn clean verify spring-boot:run` at the directory root

This project comes with the Spring actuator for absolutely no reason except you can navigate to the following URL to verify the app is up and running:

<localhost:8080/actuator/health>

If all is successful, you should see the following JSON response: `{"status":"UP"}`.

Worth noting, if the project has a bunch of red underlines make sure lombok is enabled

## The database

TODO:

## The API

the application has been designed with the following endpoints:

POST </api/v1.0/appointments>: Adds a new appointment
PUT </api/v1.0/appointments>: Updates an existing appointment
POST </api/v1.0/randomAppointment>: Adds a new appointment at a random time
GET </api/v1.0/getAppointmentsInDateRange>: Gets all appointments in date range -- note this might need to be changed
GET </api/v1.0/appointment/{appointmentId}>: Gets a specfic appointment
DELETE </api/v1.0/appointment/{appointmentId}>: Deletes a specific appointment

GET </api/v1.0/hello-world>: returns `Hello, World`

TODO: think of a domain, develop CRUD API and service with some expected failures, plus add an H2 database

## Tests

The following tests are run along with the application:

- **SmokeTest**: ensures the controllers are autowired correctly and the application can startup
- **HelloWorldControllerWebTest**: tests the basic `HelloWorldController` while starting a test web server
- **HelloWorldControllerMockMvcTest**: tests the basic `HelloWorldController` without starting a web server


