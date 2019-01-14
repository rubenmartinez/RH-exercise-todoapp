
# Red Hat exercise: Todo App

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://travis-ci.org/rubenmartinez/RH-exercise-todoapp.svg?branch=master)](https://travis-ci.org/rubenmartinez/RH-exercise-todoapp)


Note: 

The position I'm applying is "Senior Software Engineer - Cloud Technologies", so in the 2-day period allowed for the assigment, and with limited time over the weekend, I decided to focus on Cloud technologies (suck as Docker and Kubernetes), and that meant sacrificing creating a more feature reach application or even writing more tests as I would have liked. Anyway some basic user authentication and filtering tasks is there, apart from the pure Todo logic.


## Project Structure

The project consists of 2 example microservices (This might be an overkill for this exercise, but this is just for demonstration purposes of connected containers):


* The Todo backend service, which provides internal Todo management without caring about user permissions or how the Todos are used.
* The FrontEnd part, which contains the graphical user interface and it communicates with the Todo backend service to delegate all Todo management.

The Todo backend could provide service to other part of the systems, not only the frontend app, and could evolve separately from them, providing more functionality such as having todo lists, todo projects, todo priorities, todo deadlines with alarms or notifications to URLs while providing backwards compatibility to all his clients.

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

### Local/Development mode

### Docker

Container images for the two microservices have been created and uploaded to http://dockerhub.com at "rubenmartinez" repository.

#### Docker compose

A docker-compose.yml file is provided so a full stack of all the required servers can be started by executing in the project directory:

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

```
>> Server ready at http://127.0.0.1:8080/ <<
```

_Note: Better to wait a few seconds more, as with docker-compose it wasn't inmediate to implement a live probeness for mysql, that is easier on Kubernetes_

Environment variable: 

ctrl-c (or using docker-compose stop)

docker-compose down -v

Creates a volume on /tmp/todo-datavolume, so it needs to have access

### Helm/Kubernetes/Openshift

I started doing a Helm chart but didn't have time to finished it is in directory "helmchart"

Anyway, kubernetes deployment descriptors can be found at 

()


*Note: I followed these instructions to install Helm and Tiller (no admin permissions were required): https://blog.openshift.com/getting-started-helm-openshift/*


Templates specify default openshift docker registry: docker-registry.default.svc:5000, so images should be deployed there


### Local/Debug mode




Local database that is recreated every time
In this mode the logs level is set a debug



## Build

Just execute in the base directory:

    mvn clean install


## Project Structure


Note: The frontend design for the task list, has been borrowed from https://github.com/tastejs/todomvc/tree/master/examples/react, which is under [MIT License](https://github.com/astejs/todomvc/blob/master/license.md) as it looked pretty nice. (**Only** the frontend design, and after some good hours adapting it for my case)



## Usage

There are two users preloaded in the application (no time to write the user registration part :/)

* user: admin; password: admin
* user: user; password: user

Each one can have each own ToDos. If the application was started with docker compose the ToDos are persisted 

The ToDos 