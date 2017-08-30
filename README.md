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
Get IP address from inspect cmd and test connection from MySql workbench with new host IP. Run StarterDb.sql.

Build Image:
```
gradle clean buildDocker
```

Spin-up Container: 
```
docker run -d -p 8080:8080 --name ironocdb conorheffron/ironoc-db:1.5.2
```

Tear-down:
```
docker stop ironocdb
docker stop test-mysql
```

# Screenshots
![Home](https://github.com/conorheffron/ironoc-hibernate/blob/dev/screenshots/DBManager.png?raw=true "Home Page")

# TODO
Create docker compose file to spin up both MySql and ironoc-db.
