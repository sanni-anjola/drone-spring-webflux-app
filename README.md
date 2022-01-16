#Drone App

##Introduction
The Application simulates drones used for the delivery of small items that are (urgently) needed in locations with difficult access.

##Technologies
- Spring boot Web Framework
- Project Reactor
- Spring Validation - for validation
- Lombok - for boilerplate codes

  - ###Database
    - MySql - SQL database for application
    - H2 - In-memory database for testing

#####Starting the databse
To start the mysql database run the docker-compose command on the terminal
    -$ docker-compose up
this will start up the mysql db with the configurations in the application.yml file

To check the database, run
    $ docker-compose exec mysql -u user -p
and on password prompt, enter `password`

