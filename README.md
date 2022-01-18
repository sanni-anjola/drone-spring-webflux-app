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

#####Starting the database
To start the mysql database run the docker-compose command on the terminal

```shell
$ docker-compose up
``` 

this will start up the mysql db with the configurations in the application.yml file

To check the database, run

```shell
$ docker-compose exec mysql -u user -p
```

and on password prompt, enter `password`

To Build
run form the application directory (Note: You must have maven installed)
```shell
$ mvn clean install
OR
$ ./mvnw clean install
```
To Run

Java JDK or JRE must be installed.
```shell
$ java -jar ./target/drone.jar
```
### Assumptions
The following assumptions were made

1. A drone is available if it is in idle or loading state, the battery level is greater than 25% and the loaded weight is not up to the drone weight.
2. The battery of thr drone drains if not in idle state
3. The record of the battery level at a 10 seconds interval is logged and saved to `battery-levels.csv`.

