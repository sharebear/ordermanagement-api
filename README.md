# ordermanagement-api

## Description

Small REST api emulating an order management system.

The purpose of this project is to act as portfolio project to demonstrate how I think when
designing and implementing Java applications.

## Prerequisites

* JDK 8
* mvn 3
* docker daemon installed and running

## Building

Build the project, including it's docker image with the following command

```
mvn clean package docker:build
```

## Running

Run the API locally with the following command

```
docker run sharebear/ordermanagement-api:1.0-SNAPSHOT
```
