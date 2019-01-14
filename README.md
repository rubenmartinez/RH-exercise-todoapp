
# Red Hat exercise: Todo App

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://travis-ci.org/rubenmartinez/RH-exercise-todoapp.svg?branch=master)](https://travis-ci.org/rubenmartinez/RH-exercise-todoapp)


Note: the position I'm applying is "Senior Software Engineer - Cloud Technologies", so in the 2-day period allowed for the assigment, and with limited time over the weekend, I decided to focus on Cloud technologies (suck as Docker and Kubernetes), and that meant sacrificing creating a more feature reach application or even writing more tests as I would have liked. Anyway some basic user authentication and filtering tasks is there, apart from the pure Todo logic.


## Project Structure

The project consists of 2 example microservices (This might be an overkill for this exercise, but this is just for demonstration purposes of connected containers):


* The Todo backend service, which provides internal Todo management without caring about user permissions or how the Todos are used.
* The FrontEnd part, which contains the graphical user interface and it communicates with the Todo backend service to delegate all Todo management.

The Todo backend could provide service to other part of the systems, not only the frontend app, and could evolve separately from them, providing more functionality such as having todo lists, todo projects, todo priorities, todo deadlines with alarms or notifications to URLs while providing backwards compatibility to all his clients.


### Todo microservice



```bash
curl 

curl -XPOST -d'{ "ownerUserId": 1, "title": "new task", "completed": false}' -H'Content-type: application/json' 'http://127.0.0.1:8081/api/v1/todos'
curl -XPOST -d'{ "ownerUserId": 2, "title": "new task", "completed": false}' -H'Content-type: application/json' 'http://127.0.0.1:8081/api/v1/todos'

curl 'http://127.0.0.1:8081/api/v1/todos?filterOwnerId=1'

no auth, cause it would be inside intranet with no external access 
 )
```

### FrontEnd Service




Note: Actually the user management itself should go in their own service too, were users are authenticated with java web tokens, but just for the demonstration hopefully this is enough


The frontend design for the task list, has been borrowed from https://github.com/tastejs/todomvc/tree/master/examples/react, which is under [MIT License](https://github.com/astejs/todomvc/blob/master/license.md) as it looked pretty nice. (**Only** the frontend design, and after some good hours adapting it for my case, actually I must admit that I spent too much time of the assignment cause I'm not very versed in web design)

Note: In this case the frontend service is also the frontend gateway and also manages the users. Actually in a production microservice architecture, the user management and frontend gateway parts would be also moved to each own microservice, but I hope this is ok for the exercise.


## Run application

### Docker

#### Docker compose


Latest version https://docs.docker.com/compose/install/ (docker-compose.yml version '3')


```bash
$ docker-compose ps
Name                 Command            State       Ports
-------------------------------------------------------------------
composetest_redis_1   /usr/local/bin/run         Up
composetest_web_1     /bin/sh -c python app.py   Up      5000->5000/tcp
```

```
>> Server ready at http://127.0.0.1:8080/ <<
```

_Note: As it doesn't use any live probeness for mysql, better to wait some additional seconds just in case_

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