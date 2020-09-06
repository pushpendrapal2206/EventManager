- [Table of content](#table-of-content)
  * [Introduction](#introduction)
    + [Getting Started](#getting-started)
      - [Prerequisites](#prerequisites)
      - [Installing](#installing)
  * [Running the tests](#running-the-tests)
      - [Unit Testing](#unit-testing)
      - [Regression Testing](#regression-testing)
  * [Deployment](#deployment)
    - [Built With](#built-with)
    - [Authors](#authors)


## Introduction

Event manager receives several incoming requests from clients and reports about the 'ping' status with the client's identification and also reports an idle timeout after a given threshold limit is reached without any activities.

### Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

#### Prerequisites

For running this project, you need to have Java 8 and Maven 3.3.9 at least.

For checking which versions you have, you can run the following commands:

```
java --version

mvn --version
```

For working with maven, you can read the basics from apache's official precise documentation - [*Maven in 5 minutes*](
https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

#### Installing

A step by step series of examples that tell you how to get the application up and running

You can run the following 
commands to run the application. From `EventManager` run below command. 

I. This command will run unit tests and if all the tests are passed then it will start the application

```
EventManager $ ./bin/setup config/config.yml
```
II. To test functionality. Please run below commands after running command mentioned in above step I.
```
EventManager $ ./functional/client-requests.sh
```
This will send multiple event requests to our event manager.
And then using below command we can query the status of our clients.
```
EventManager $ ./functional/client-status-query.sh
```

### Running the tests

#### Unit Testing

The other corresponding stats are as follows:

**Unit Test Coverage**: 92%

**Line Coverage** : 87%

**Method Coverage** : 91%

For running all these tests you can follow below step:

I. Run the maven test phase from `even-manager` folder.
```
mvn test
```


### Built With

* [Maven](https://maven.apache.org/) - Dependency Management
 
### Authors

* **Pushpendra Pal** - *event-manager* - [event-manager]
