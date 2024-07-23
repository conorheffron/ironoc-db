[![Java CI with Gradle](https://github.com/conorheffron/ironoc-db/actions/workflows/gradle.yml/badge.svg)](https://github.com/conorheffron/ironoc-db/actions/workflows/gradle.yml)

![Proof HTML](https://github.com/conorheffron/ironoc-db/actions/workflows/proof-html.yml/badge.svg)

![Auto Assign](https://github.com/conorheffron/ironoc-db/actions/workflows/auto-assign.yml/badge.svg)

# Sample Data Manager
================

## Docker Hub
[ironoc-db docker hub](https://hub.docker.com/repository/docker/conorheffron/ironoc-db/general)

## Summary
This project is a sample data manager. It provides a basic template for Java/Spring developers. This project also includes form validation of controller model objects and request parameters.
Users can view, add, delete person objects from the database via web UI.

## Technologies Used
Java 21 (LTS Version), Spring Boot 3, Hibernate, MySQL, JSP, Gradle 8.5

## Run
MySql
```
docker run -p 3306:3306 -d --name test-mysql -e "MYSQL_ROOT_PASSWORD=mypassword" mysql
docker logs test-mysql
docker inspect test-mysql
```

![create-db-connection](./screenshots/db-connection.png?raw=true "Create DB Connection")
![create-test-schema](./screenshots/create-schema.png?raw=true "Create Test Schema")
![load-db](./screenshots/run-starter-db-script.png?raw=true "Load DB")
![verify-db](./screenshots/verify-db-load.png?raw=true "Verify DB")

## Create Network
```
docker network create my-network
docker inspect network my-network 
```

## Link container to same network for access:
```
docker network connect my-network test-mysql
```

## Inspect network configurations & update application properties with IPv4Address instead of localhost if mac user (IPv4Address for my-sql etc.)
- Get IP address from inspect cmd and test connection from MySql workbench with new host IP. Run StarterDb.sql.
```
docker inspect network my-network 
```

## Build ironoc-db, run unit & integration tests, & generate war file.
```
gradle clean build
```

## Run 'com.ironoc.db.App.java' directly from IntelliJ (can use localhost for spring.datasource.url) or via CLI (build & spin up docker image, use docker network IP address for test-mysql process):
```
docker image build -t ironoc-db .
docker compose up -d
```
![docker-cli](./screenshots/CLI-docker.png?raw=true "CLI Docker")

## Tear-down:
```
docker stop test-mysql
docker remove test-mysql
```

## Alternatively, Docker Desktop is good if you prefer to not use the terminal/command line (CLI)
![docker-desktop-containers](./screenshots/docker-desktop-containers.png?raw=true "Docker Desktop containers")

## Can tail the logs by scrolling within the container logs:
![docker-desktop-ironoc-db-logs](./screenshots/docker-desktop-ironoc-db-logs.png?raw=true "Docker Desktop ironoc-db logs")

## Screenshot Home
![Home](./screenshots/DBManager.png?raw=true "Home Page")

## Screenshot Form Validation Error for Add Person Call
![ui-form-validation](./screenshots/ui-form-validation.png?raw=true "UI form validation")

## Screenshot Form Validation Error for Delete Operation
![ui-delete-validation](./screenshots/ui-delete-validation.png?raw=true "UI delete validation")


