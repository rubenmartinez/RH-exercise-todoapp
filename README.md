
# Containers exercise: Todo App

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://travis-ci.org/rubenmartinez/RH-exercise-todoapp.svg?branch=master)](https://travis-ci.org/rubenmartinez/RH-exercise-todoapp)


**Preliminary Note:**

The position I'm applying is "_Senior Software Engineer - Cloud Technologies_", so in the 2-day period allowed for the assigment, and with limited time over the weekend, I decided to focus on Cloud technologies (suck as Docker and Kubernetes), and that meant sacrificing creating a more feature reach application or even writing more tests as I would have liked. Anyway some basic user authentication and filtering tasks is there, apart from the pure Todo logic.

## Project Structure

The project consists of 2 example microservices (This might be an overkill for this exercise, but this is just for demonstration purposes of connected containers):


* **The Todo backend service**: provides internal Todo management without caring about user permissions or how the Todos are used.
* **The FrontEnd service**: which contains the graphical user interface and it communicates with the Todo backend service to delegate all Todo management.

Ideally the Todo backend could provide service to other part of the systems, not only the frontend app, and could evolve separately from them, providing more functionality such as having todo lists, todo projects, todo priorities, todo deadlines with alarms or notifications to URLs while providing backwards compatibility to all his clients.

For simpliticy a main pom with artifactId "redhat-exercise", builts the two microservices as modules.

### Todo microservice

Module: rhe-todo

A SpringBoot server that manages Todo lifecycles, and could potentially be extended with more functionality. This microservice would be running in an intranet, so it doesn't have to deal with authorization (even if for some microservices that is desirable, even in intranets). This schema allows to test or to directly create requests to the microservice via http commands, without dealing about authentication).

Example, if the microservice is running locally on port 8081:

```bash

# Create two tasks
curl -XPOST -d'{ "ownerUserId": 1, "title": "new task1", "completed": false}' -H'Content-type: application/json' 'http://127.0.0.1:8081/api/v1/todos'
curl -XPOST -d'{ "ownerUserId": 1, "title": "new task2", "completed": false}' -H'Content-type: application/json' 'http://127.0.0.1:8081/api/v1/todos'

# Retrieve the tasks created
curl 'http://127.0.0.1:8081/api/v1/todos?filterOwnerId=1'
```

### FrontEnd Service

The FrontEnd service was created using Springboot for the backend and Vue.js for the frontend part (after a first try in React). The frontend design for the task list, has been borrowed from https://github.com/tastejs/todomvc/tree/master/examples/react, which is under [MIT License](https://github.com/astejs/todomvc/blob/master/license.md) as it looked pretty nice. (**Only** the frontend design, and after some good hours adapting it for the exercise, actually I must admit that I spent too much time of the assignment cause I'm not very versed in web design).

Note: In this case the frontend service is also the frontend gateway and also manages the users. Actually in a production microservice architecture, the user management and frontend gateway parts would be also moved to each own microservice, but I hope this is ok for the exercise.


## Run application

### Docker

A repository for each microservice have been created and uploaded to http://dockerhub.com.

* **rhe-todo**: https://hub.docker.com/r/rubenmartinez/rhe-todo
* **rhe-fe**: https://hub.docker.com/r/rubenmartinez/rhe-fe

#### Docker compose

A docker-compose.yml file is provided so a full stack of all the required servers can be started, including databases, by executing in the project directory:

```bash
docker-compose up
```

This will start one MySQL Server for the FrontEnd Service, another MySQL for the Service for the ToDo Service, and the 2 microservices themselves:

(Assuming docker compose is installed in the local machine, actually a recent version is required docker-compose.yml version '3').

We can check the services started:


```bash
$ docker-compose ps
              Name                            Command               State                Ports
------------------------------------------------------------------------------------------------------------
rh-exercise-todoapp_fe_1           /usr/bin/java -jar /app/rh ...   Up      0.0.0.0:8080->8080/tcp
rh-exercise-todoapp_mysql-fe_1     docker-entrypoint.sh mysqld      Up      0.0.0.0:3306->3306/tcp
rh-exercise-todoapp_mysql-todo_1   docker-entrypoint.sh mysqld      Up      3306/tcp, 0.0.0.0:3307->3307/tcp
rh-exercise-todoapp_todo_1         /usr/bin/java -jar /app/rh ...   Up      8080/tcp, 0.0.0.0:8081->8081/tcp
```


The docker-compose descriptor instructs docker to create a new volume on directory `/tmp/todo-datavolume` (so it needs to have access to /tmp directory).

This volume will be used to store the todos, so even if the containers are stopped or restarted, the Todos will be maintained.

To remove the volume and todos you can execute:

```
docker-compose down -v
```

#### Just Docker

In "production mode", the microservices need a mysql database to store users and todos. This MySQL Database is automatically setup when using docker-compose (see previous section) without any extra needed steps, but it is possible to execute the microservices using an existing MySQL Server if available, by executing:

```bash
docker network create rhe
```
```bash
docker run --rm -p8081:8081 --network rhe -eDB_DATASOURCE=jdbc:mysql://[mysql host and port]/todoapp rubenmartinez/rhe-todo
```
```bash
docker run --rm -p8080:8080 --network rhe -eDB_DATASOURCE=jdbc:mysql://[mysql host and port]/todoapp rubenmartinez/rhe-fe
```

In this case the MySQL Server specified should contain a Database called 'todoapp', which access to two users: `rhe-todo` (password: `rhe-todo`), and `rhe-fe` (password: `rhe-fe`).


#### Docker images creation

The Docker images are created directly from the maven deployment process, thanks to the [fabric8io plugin](https://github.com/fabric8io/docker-maven-plugin). To rebuild and push one images you can go the module directory (rhe-todo or rhe-fe) and execute:

```
mvn clean install -Pdocker -DskipTests=true
```


### Helm/Kubernetes/Openshift

I started doing a Helm chart but didn't have time to finished it on time. 

Anyway, in case you want to have a look you can find kubernetes deployment descriptors can be found at the [templates directory](https://github.com/rubenmartinez/RH-exercise-todoapp/tree/master/helmchart/templates).


### Local/Debug mode

If docker and/or docker-compose are not installed or there is any problem with previous steps, the application can be still tested in development mode.

In this case SpringBoot will automatically use an embedded database so no need for any other configuration is required.

Note that as no external DBs are used, all ToDos will be lost when the _Todos_ server is restarted, but, if only the FE microservice is restarted, the ToDos will be preserved as expected and the user will be able to continue using the application after the restart.

To start the servers using the development profile, first create the binary packages if not already created with:

```bash
mvn clean package
```

The main pom already copies the executable jars in the project base directory, so we can start each of them by doing in the base directory:

```bash
java -jar target/rhe-todo-0.0.1.jar --spring.profiles.active=devel
```

```bash
java -jar target/rhe-app-0.0.1.jar --spring.profiles.active=devel
```

You will notice the log levels are much more verbose too.


## Application Usage

First, after the applicationWait for this message to apear in the logs:

```
>>>>>>>>>>>>>>>>>>>> Server ready at http://127.0.0.1:8080/ <<<<<<<<<<<<<<<<<<<<
```

It needs to be the port 8080 and not 8081, which is the port used for the front-end part. (_Note: When starting the services via docker-compose, it is better to wait a few seconds more, as with docker-compose it wasn't inmediate to implement a live probeness for mysql, that is easier on Kubernetes, this wait is not necessary in develpment mode with an embedded database_)

There are two users preloaded in the application (no time to write the user registration part :/)

* user: **admin**; password: **admin**
* user: **user**; password: **user**

Each use will have each own ToDo list. If the application was started with docker compose, the ToDos will be persisted even if the servers are stopped and restarted again (as long as the host machine is not restarted, as the DB is kept in the /tmp directory).

There is a small (FE-based) feature also to filter tasks by title substring. There is also a link to remove all completed tasks, which will cause the tasks to be deleted also from Database.


