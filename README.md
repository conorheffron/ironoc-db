[![Java CI with Gradle](https://github.com/conorheffron/ironoc-db/actions/workflows/gradle.yml/badge.svg)](https://github.com/conorheffron/ironoc-db/actions/workflows/gradle.yml)

![Proof HTML](https://github.com/conorheffron/ironoc-db/actions/workflows/proof-html.yml/badge.svg)

![Auto Assign](https://github.com/conorheffron/ironoc-db/actions/workflows/auto-assign.yml/badge.svg)

# Sample Data Manager
================

## Docker Hub
[ironoc-db docker hub](https://hub.docker.com/repository/docker/conorheffron/ironoc-db/general)

## Summary
This project is a sample data manager. It provides a basic template for Java/Spring developers. 
This project also includes form validation of controller model objects and request parameters.
Users can view, add, delete person objects from the database via web UI.

## Technologies Used
Java 21 (LTS Version), Spring Boot 3, Hibernate, MySQL or H2 databases supported, JSP, Gradle 8.10, 
    GKE, Docker, minikube, & kubectl.

## Run
### - See db.StarterDb.sql for sample Schema to get started with ironoc-db
MySql
```
docker pull mysql:latest
docker run -d --name test-mysql -e MYSQL_ROOT_PASSWORD=mypassword -p 3307:3306 mysql
docker logs test-mysql
docker exec -it test-mysql bash
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

## Link mysql container to same network for access:
```
docker network connect my-network <mysql_container_name:test-mysql>
```

## Inspect network configurations & update application properties with IPv4Address instead of localhost if mac user (IPv4Address for my-sql etc.)
- Get IPv4Address from inspect cmd and test connection from MySql workbench with new host IP. Run StarterDb.sql.
```
docker inspect network my-network 
```

## Build ironoc-db, run unit & integration tests, & generate war file.
```
gradle clean build
```

## Run 'com.ironoc.db.App.java' directly from IntelliJ (can use localhost for spring.datasource.url) or 
## via CLI (build & spin up docker image, use docker network IP address for test-mysql process):
```
docker image build -t ironoc-db .
docker compose up -d
docker logs ironoc-db-web-1 -f
```

### Run locally with Gradle & H2 database
```
gradle bootRun --args='--spring.profiles.active=h2'
```

### Run locally with Gradle & MySQL database
```
gradle bootRun --args='--spring.profiles.active=default'
```

![docker-cli](./screenshots/CLI-docker.png?raw=true "CLI Docker")

## Tear-down:
```
docker stop test-mysql
docker remove test-mysql
```

## Run ironoc-db on local k8s cluster
```    
minikube start --driver=docker

kubectl cluster-info 

minikube dashboard 
```
— Then change namespace in browser after creation of 'ironoc-db' namespace.

```
docker images

docker image build -t ironoc-db .
minikube image load ironoc-db:latest

kubectl create ns ironoc-db-ns
kubectl get ns

kubectl apply -f ./kubernetes/ironoc-db-local.yml --namespace=ironoc-db-ns 

kubectl get pods --namespace=ironoc-db-ns
kubectl get deployment --namespace=ironoc-db-ns

kubectl expose deployment ironoc-db-app-deployment --type=NodePort --namespace=ironoc-db-ns
kubectl get services --namespace=ironoc-db-ns

minikube service ironoc-db-app-deployment --url --namespace=ironoc-db-ns
```
Open a new terminal tab & follow the logs
```
% kubectl get pods --namespace=ironoc-db-ns
NAME                                        READY   STATUS    RESTARTS   AGE
ironoc-db-app-deployment-6c566784bc-q29xs   1/1     Running   0          2m40s

% kubectl logs ironoc-db-app-deployment-6c566784bc-q29xs -f --namespace=ironoc-db-ns   
```  

![minikube-dash](./screenshots/minikube-dash.png?raw=true "Minikube Dashboard")

![deployment](./screenshots/deployment.png?raw=true "ironoc-db local k8s Deployment")

```  
minikube delete  
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


