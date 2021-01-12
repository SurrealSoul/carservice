# Car Service Center

## Overview
This application is used for creating simple appointments for a car service center. 

Maven is required for this project, `brew install maven` in order to run this project you can use Intellij's spring boot configuration, or through the command line via `mvn clean verify spring-boot:run` at the directory root

This project comes with the Spring actuator for absolutely no reason except you can navigate to the following URL to verify the app is up and running:

<localhost:8080/actuator/health>

If all is successful, you should see the following JSON response: `{"status":"UP"}`.

Worth noting, if the project has a bunch of red underlines make sure lombok is enabled

## The Database
I am using a postgres database for this project, you can install postgres with `brew install psgl`
You'll then need to create your own database
* enter the psql commandline via `psql`
* `create database carservice;`
* `create user carservice;`
* `alter role carservice with password root;`

Then run `mvn flyway:migrate` to get the schema applied. -- This might need to be ran twice for the first time, as you may need a baseline

Testing uses an in memory h2 database.

## The API

the application has been designed with the following endpoints:

* POST </api/v1.0/appointments>: Adds a new appointment -- requires an AppointmentDTO request body
* PUT </api/v1.0/appointments/{appointmentId}>: Updates an existing appointment -- requires an AppointmentDTO request body
* POST </api/v1.0/appointments/randomAppointment>: Adds a new appointment at a random time -- Between 2020-01-01 and 2030-01-01
* GET </api/v1.0/appointments/{startDate}/{endDate}>: Gets all appointments in date range ordered by price. Note requires dates in `yyyy-MM-dd hh:mm:ss` format
* GET </api/v1.0/appointments/{appointmentId}>: Gets a appointment by ID
* DELETE </api/v1.0/appointments/{appointmentId}>: Deletes a specific appointment

* POST </api/v1.0/cars>: Adds a new car object -- requires a CarDTO request body
* PUT </api/v1.0/cars/{carId}>: Updates an existing car object -- requires a CarDTO request body
* GET </api/v1.0/cars/{carId}>: Gets an existing car object
* DELETE </api/v1.0/cars/{carId}>: Deletes an existing car object. Note if a car is a part of an appointment you can not delete it.

* POST </api/v1.0/users>: Adds a new User object -- requires a UserDTO request body
* PUT </api/v1.0/users/{userId}>: Updates an existing user object -- requires a UserDTO request body. Note usernames are unique
* GET </api/v1.0/users/{userId}>: Gets an existing user object

* GET </api/v1.0/hello-world>: returns `Hello, World` -- just a smoke test

## Domain Objects

As mentioned above there are three domain objects for interacting with the api

AppointmentDTO example
```
{
    "status": "pending",
    "date_time": "2020-01-01 00:00:00,
    "car_id": 2,
    "user_id": 3,
    "price": 300,
    "service": "Fix my car"
}
```


CarDTO example
```
{
    "make": "landcruiser",
    "model": "toyota",
    "year": "1995",
    "user_id": 3
}
```


UserDTO example
```
{
    "first_name": "drew",
    "last_name": "davis",
    "email": "andrewcdavis0@gmail.com",
    "username": "andrewcdavis0",
    "phone": "5133285317"
}
```


## Tests
- Build and test the application: `mvn clean test install -Dspring.profiles.active=test`
- You can also run the tests via Junit in intellij

The following tests are ran with the application:

* **SmokeTest**: ensures the controllers are autowired correctly and the application can startup
* **HelloWorldControllerWebTest**: tests the basic `HelloWorldController` while starting a test web server
* **HelloWorldControllerMockMvcTest**: tests the basic `HelloWorldController` without starting a web server
* **AppointmentServiceTest**: tests the appointment service functions
* **CarServiceTest**: tests the car service functions
* **UserServiceTest**: test the user service functions
* **AppointmentRepositoryTest**: test the appointment repository
* **CarRepositoryTest**: test the car repository


## Future Improvements
* Dockerize this thing: This is something else that I have limited experience in. I am familar with managing and poking around with docker containers, but I never had the pleasure of actually creating pattern. In the current state, we would have to create a jar of this project to run on our server. This has it's own problems but if it was running in a dockerized instence it would be less of a headache to manage.
* Enum value in database for status: You may have noticed that I am storing the status value as a string in the database. I tried setting an ENUM in postgres and requesting this value, however, I could not get tha to work the way I wanted. As a temporary shortstop for now, the database stores a string value for status, while the application uses ENUMs.
* Better response objects?: Without knowing the full context of what these endpoints are doing, I decided to be general with my response objects, only returning the object you modified. However, it would not be unnatural to want to return an appointment object that has car and user details as well, but maybe that contains PII. For now I choose to be general, but in the future we may want different return values to be established.
* Security: These rest points could be accessed by anyone, maybe we should add some OAUTH or something where applicable
* Entity Deletion: If an entity is attached to another entity (ie: Car to appointment, user to appointment) we would have to delete all appointments associated with that entity to remove it. We could make it optional in the appointment, but this constrant makes sense to me. We can not have a car service appointment without a car or a user.
