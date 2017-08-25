Sample Data Manager
================

# Summary
This project is a sample data manager. It provides a basic template for Java/Spring developers. This project also includes form validation of controller model objects and request parameters.

# Technologies
Java 8, Spring Boot, Hibernate, MySQL, JSP

# Run
MySql
```
docker run --detach --name=test-mysql --env="MYSQL_ROOT_PASSWORD=mypassword" mysql
docker logs test-mysql
docker inspect test-mysql
```
Get IP address from inspect cmd and test connection from MySql workbench with new host IP. Run StarterDb.sql.

Build Image:
```
mvn clean package docker:build 
```

Spin-up Container: 
```
docker-compose up -d
```

Tear-down:
```
docker-compose down
```

# Screenshots
![Home](https://github.com/conorheffron/ironoc-hibernate/blob/dev/screenshots/DBManager.png?raw=true "Home Page")

# TODO
Create docker compose file to spin up both MySql and ironoc-db.
