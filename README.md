# ordermanagement-api

## Description

Small REST api emulating an order management system.

The purpose of this project is to act as portfolio project to demonstrate how I think when
designing and implementing Java applications.

## Prerequisites

* JDK 8
* mvn 3
* docker daemon installed and running
* AWS user with read and write access to a DynamoDB table called users. You may use the
  ordermanagement-terraform project to configure such resources.

## Building

Build the project, including it's docker image with the following command

```
mvn clean package docker:build
```

## Configuration

If you wish to run this project on a host that is not within the AWS infrastructure, i.e. a local
development machine then you need to ensure that AWS credentials are available for the AWS client
library. The simplest way of doing this is to update the .env file example provided in this
repository.

## Running

Run the API locally with the following command

```
docker run -p 4567:4567 --env-file .env sharebear/ordermanagement-api:1.0-SNAPSHOT
```
