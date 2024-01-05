Sample Data Manager
================

# Summary
This project is a sample data manager. It provides a basic template for Java/Spring developers. This project also includes form validation of controller model objects and request parameters.

# Technologies
Java 8, Spring Boot, Hibernate, MySQL, JSP, Gradle

# Run
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

Get IP address from inspect cmd and test connection from MySql workbench with new host IP. Run StarterDb.sql.

Build Image:
```
gradle clean buildDocker
```

Spin-up Container: 
```
docker run -d -p 8080:8080 --name ironocdb conorheffron/ironoc-db:1.5.3
```

Tear-down:
```
docker stop ironocdb
docker remove ironocdb
```
```
docker stop test-mysql
docker remove test-mysql
```

# Screenshots
![Home](./screenshots/DBManager.png?raw=true "Home Page")

# TODO
Create docker compose file to spin up both MySql and ironoc-db.
