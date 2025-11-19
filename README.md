# ironoc-db

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

[![Gradle Package](https://github.com/conorheffron/ironoc-db/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/conorheffron/ironoc-db/actions/workflows/gradle-publish.yml)

[![Java CI with Gradle](https://github.com/conorheffron/ironoc-db/actions/workflows/gradle.yml/badge.svg)](https://github.com/conorheffron/ironoc-db/actions/workflows/gradle.yml)

[![Codacy Security Scan](https://github.com/conorheffron/ironoc-db/actions/workflows/codacy.yml/badge.svg)](https://github.com/conorheffron/ironoc-db/actions/workflows/codacy.yml)

[![Docker Image CI](https://github.com/conorheffron/ironoc-db/actions/workflows/docker-image.yml/badge.svg)](https://github.com/conorheffron/ironoc-db/actions/workflows/docker-image.yml)

[![Docker Publish](https://github.com/conorheffron/ironoc-db/actions/workflows/docker-publish.yml/badge.svg)](https://github.com/conorheffron/ironoc-db/actions/workflows/docker-publish.yml)

![Proof HTML](https://github.com/conorheffron/ironoc-db/actions/workflows/proof-html.yml/badge.svg)

![Auto Assign](https://github.com/conorheffron/ironoc-db/actions/workflows/auto-assign.yml/badge.svg)

## Sonar (Static Application Security Testing SAST tool)

[![SonarQube](https://github.com/conorheffron/ironoc-db/actions/workflows/sonar.yml/badge.svg)](https://github.com/conorheffron/ironoc-db/actions/workflows/sonar.yml)

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=conorheffron_ironoc-db)](https://sonarcloud.io/summary/new_code?id=conorheffron_ironoc-db)

[Sonar Scan Analysis & JaCoCo Test Coverage Report - Overall Summary](https://sonarcloud.io/summary/overall?id=conorheffron_ironoc-db&branch=main)

# Sample Data Manager
================

## Docker Hub
[ironoc-db docker hub](https://hub.docker.com/repository/docker/conorheffron/ironoc-db/general)

## Summary
This project is a sample data manager. It provides a basic template for Java/Spring developers. 
This project also includes form validation of controller model objects and request parameters.
Users can view, add, delete person objects from the database via web UI.

## Technologies Used
Java 25, Spring Boot 3, Thymeleaf Templates, Hibernate, MySQL or H2 databases supported, Gradle 9, 
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
```shell
docker inspect network my-network 
```

## Build ironoc-db, run unit & integration tests, & generate war file.
```shell
./gradlew clean build
```

## Login to gcloud project for authentication tokens etc. for save to local workspace
### Accept & allow browser prompts.
### Note: This is for direct app run, need to to do this in container or pod for virtualization.
```shell
gcloud auth application-default login
```

## Verify credentials (set appropriate svc / user account & project name)
```shell
gcloud config list
```

## Run 'com.ironoc.db.App.java' directly from IntelliJ (can use localhost for spring.datasource.url) or 
## via CLI (build & spin up docker image, use docker network IP address for test-mysql process):
```shell
docker image build -t ironoc-db .
```
```shell
docker compose up -d
```
```shell
docker ps
```
```shell
docker logs ironoc-db-web-1 -f
```
```shell
docker compose down
```

### Run locally with Gradle & H2 database
```
./gradlew bootRun --args='--spring.profiles.active=h2'
```

### Run locally with Gradle & MySQL database
```
./gradlew bootRun --args='--spring.profiles.active=default'
```

![docker-cli](./screenshots/CLI-docker.png?raw=true "CLI Docker")

## Tear-down:
```
docker stop test-mysql
docker remove test-mysql
```

## Run ironoc-db on local k8s cluster
```shell  
%   minikube start --driver=docker  
😄  minikube v1.35.0 on Darwin 15.3.1
❗  Both driver=docker and vm-driver=virtualbox have been set.

    Since vm-driver is deprecated, minikube will default to driver=docker.

    If vm-driver is set in the global config, please run "minikube config unset vm-driver" to resolve this warning.
                        
✨  Using the docker driver based on user configuration
📌  Using Docker Desktop driver with root privileges
👍  Starting "minikube" primary control-plane node in "minikube" cluster
🚜  Pulling base image v0.0.46 ...
💾  Downloading Kubernetes v1.32.0 preload ...
    > preloaded-images-k8s-v18-v1...:  333.57 MiB / 333.57 MiB  100.00% 22.49 M
    > gcr.io/k8s-minikube/kicbase...:  500.31 MiB / 500.31 MiB  100.00% 14.30 M
🔥  Creating docker container (CPUs=2, Memory=4000MB) ...
🐳  Preparing Kubernetes v1.32.0 on Docker 27.4.1 ...
    ▪ Generating certificates and keys ...
    ▪ Booting up control plane ...
    ▪ Configuring RBAC rules ...
🔗  Configuring bridge CNI (Container Networking Interface) ...
🔎  Verifying Kubernetes components...
    ▪ Using image gcr.io/k8s-minikube/storage-provisioner:v5
🌟  Enabled addons: storage-provisioner, default-storageclass
🏄  Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default


%   kubectl cluster-info 
Kubernetes control plane is running at https://127.0.0.1:54912
CoreDNS is running at https://127.0.0.1:54912/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy

To further debug and diagnose cluster problems, use 'kubectl cluster-info dump'.


%   minikube dashboard 
🔌  Enabling dashboard ...
    ▪ Using image docker.io/kubernetesui/metrics-scraper:v1.0.8
    ▪ Using image docker.io/kubernetesui/dashboard:v2.7.0
💡  Some dashboard features require the metrics-server addon. To enable all features please run:

        minikube addons enable metrics-server

🤔  Verifying dashboard health ...
🚀  Launching proxy ...
🤔  Verifying proxy health ...
🎉  Opening http://127.0.0.1:54976/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/ in your default browser...
```
- Then change namespace in browser after creation of 'ironoc-db' namespace.

![minikube-dash](./screenshots/minikube-dash.png?raw=true "Minikube Dashboard")
```shell
%   docker images
REPOSITORY                    TAG       IMAGE ID       CREATED        SIZE
gcr.io/k8s-minikube/kicbase   v0.0.45   e7c9bc3bc515   3 months ago   1.81GB
gcr.io/k8s-minikube/kicbase   <none>    81df28859520   3 months ago   1.81GB


%   docker image build -t ironoc-db .
[+] Building 174.7s (11/11) FINISHED                                                                                                                                                                                                                                            docker:desktop-linux
 => [internal] load build definition from Dockerfile                                                                                                                                                                                                                                            0.0s
 => => transferring dockerfile: 340B                                                                                                                                                                                                                                                            0.0s
 => [internal] load metadata for docker.io/library/gradle:8.13.0-jdk23-alpine                                                                                                                                                                                                                   1.4s
 => [auth] library/gradle:pull token for registry-1.docker.io                                                                                                                                                                                                                                   0.0s
 => [internal] load .dockerignore                                                                                                                                                                                                                                                               0.0s
 => => transferring context: 2B                                                                                                                                                                                                                                                                 0.0s
 => [internal] load build context                                                                                                                                                                                                                                                               0.6s
 => => transferring context: 2.48MB                                                                                                                                                                                                                                                             0.6s
 => CACHED [1/5] FROM docker.io/library/gradle:8.13.0-jdk23-alpine@sha256:2deae0129d5d2659e5aa890138420398708c7ab54250985bbfb08046611d8e27                                                                                                                                                      3.5s
 => => resolve docker.io/library/gradle:8.13.0-jdk23-alpine@sha256:2deae0129d5d2659e5aa890138420398708c7ab54250985bbfb08046611d8e27                                                                                                                                                             3.4s
 => [2/5] COPY . /home/gradle                                                                                                                                                                                                                                                                   6.2s
 => [3/5] RUN apk update && apk upgrade --no-cache                                                                                                                                                                                                                                              7.9s
 => [4/5] RUN apk add gcompat                                                                                                                                                                                                                                                                   1.4s 
 => [5/5] RUN gradle build                                                                                                                                                                                                                                                                    117.0s 
 => exporting to image                                                                                                                                                                                                                                                                         36.3s 
 => => exporting layers                                                                                                                                                                                                                                                                        25.4s 
 => => exporting manifest sha256:38618720756428d821efcfa62dc4ec9a30695aceeb1c1109a628350a85f6ba34                                                                                                                                                                                               0.0s 
 => => exporting config sha256:774368a010c8731bd8287ad62fb7b3c7744642dc398a5cefe72bbe49b4666c45                                                                                                                                                                                                 0.0s 
 => => exporting attestation manifest sha256:723a48372e275ddd28aa039aef88bfe1ca5bf9f7cb59f4bfb9c9679b90faca9e                                                                                                                                                                                   0.0s 
 => => exporting manifest list sha256:446306dc3c57370dfb771b12a1d538cf1e6c40fb749065dfae9cecc045d6da9f                                                                                                                                                                                          0.0s 
 => => naming to docker.io/library/ironoc-db:latest                                                                                                                                                                                                                                             0.0s
 => => unpacking to docker.io/library/ironoc-db:latest                                                                                                                                                                                                                                         10.7s

View build details: docker-desktop://dashboard/build/desktop-linux/desktop-linux/wbu3i027e2wyq3oi661jykphk

What's next:
    View a summary of image vulnerabilities and recommendations → docker scout quickview 


%   docker images                       
REPOSITORY                    TAG       IMAGE ID       CREATED         SIZE
ironoc-db                     latest    1e287e0f161e   7 minutes ago   1.79GB
gcr.io/k8s-minikube/kicbase   v0.0.45   e7c9bc3bc515   3 months ago    1.81GB
gcr.io/k8s-minikube/kicbase   <none>    81df28859520   3 months ago    1.81GB


%   minikube image load ironoc-db:latest

%   kubectl create ns ironoc-db-ns
namespace/ironoc-db-ns created


%   kubectl get ns
NAME                   STATUS   AGE
default                Active   27m
ironoc-db-ns           Active   7s
kube-node-lease        Active   27m
kube-public            Active   27m
kube-system            Active   27m
kubernetes-dashboard   Active   14m


%   kubectl apply -f ./kubernetes/ironoc-db-local.yml --namespace=ironoc-db-ns 
deployment.apps/ironoc-db-app-deployment created
horizontalpodautoscaler.autoscaling/ironoc-db-app-deployment-hpa-kbij created


%   kubectl get pods --namespace=ironoc-db-ns
NAME                                        READY   STATUS    RESTARTS   AGE
ironoc-db-app-deployment-d5c59b4c5-rzmhz   1/1     Running   0          10s


%   kubectl get deployment --namespace=ironoc-db-ns
NAME                       READY   UP-TO-DATE   AVAILABLE   AGE
ironoc-db-app-deployment   1/1     1            1           17s
```
![minikube-dash-deployments](./screenshots/minikube-dash-deployments.png?raw=true "ironoc-db kube Deployment")
```shell
%   kubectl expose deployment ironoc-db-app-deployment --type=NodePort --namespace=ironoc-db-ns

service/ironoc-db-app-deployment exposed


%   kubectl get services --namespace=ironoc-db-ns
NAME                       TYPE       CLUSTER-IP    EXTERNAL-IP   PORT(S)          AGE
ironoc-db-app-deployment   NodePort   10.96.135.232   <none>        8080:31797/TCP   8s


%   minikube service ironoc-db-app-deployment --url --namespace=ironoc-db-ns
http://127.0.0.1:55519
❗  Because you are using a Docker driver on darwin, the terminal needs to be open to run it.
```
- Open a new terminal tab & follow the logs
```shell
%   kubectl get pods --namespace=ironoc-db-ns
NAME                                        READY   STATUS    RESTARTS   AGE
ironoc-db-app-deployment-d5c59b4c5-rzmhz   1/1     Running   0          2m54s


%   kubectl logs ironoc-db-app-deployment-d5c59b4c5-rzmhz -f --namespace=ironoc-db-ns
Starting a Gradle Daemon, 1 incompatible and 1 stopped Daemons could not be reused, use --status for details
> Task :bootBuildInfo
> Task :compileJava UP-TO-DATE
> Task :processResources UP-TO-DATE
> Task :classes
> Task :resolveMainClassName

> Task :bootRun

.__ __________                                  ________             __           ___.                            
|__|\______   \  ____    ____    ____    ____   \______ \  _____   _/  |_ _____   \_ |__  _____     ______  ____  
|  | |       _/ /  _ \  /    \  /  _ \ _/ ___\   |    |  \ \__  \  \   __\\__  \   | __ \ \__  \   /  ___/_/ __ \ 
|  | |    |   \(  <_> )|   |  \(  <_> )\  \___   |    `   \ / __ \_ |  |   / __ \_ | \_\ \ / __ \_ \___ \ \  ___/ 
|__| |____|_  / \____/ |___|  / \____/  \___  > /_______  /(____  / |__|  (____  / |___  /(____  //____  > \___  >
            \/              \/              \/          \/      \/             \/      \/      \/      \/      \/ 


2025-05-20T00:46:38.947Z  INFO 150 --- [           main] com.ironoc.db.App                        : Starting App using Java 24.0.1 with PID 150 (/home/gradle/build/classes/java/main started by root in /home/gradle)
2025-05-20T00:46:38.952Z  INFO 150 --- [           main] com.ironoc.db.App                        : The following 1 profile is active: "h2"
2025-05-20T00:46:40.736Z  INFO 150 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2025-05-20T00:46:40.816Z  INFO 150 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 67 ms. Found 1 JPA repository interface.
2025-05-20T00:46:42.198Z  INFO 150 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2025-05-20T00:46:42.262Z  INFO 150 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2025-05-20T00:46:42.263Z  INFO 150 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/11.0.7]
2025-05-20T00:46:42.708Z  INFO 150 --- [           main] org.apache.jasper.servlet.TldScanner     : At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
2025-05-20T00:46:42.718Z  INFO 150 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2025-05-20T00:46:42.719Z  INFO 150 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 3603 ms
2025-05-20T00:46:42.802Z  INFO 150 --- [           main] c.i.db.service.GoogleCloudClientImpl     : Entering GoogleCloudClient.getSecret for secretVersion=projects/902038140834/secrets/MY_SQL_PASSWORD/versions/latest
2025-05-20T00:46:43.377Z  INFO 150 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2025-05-20T00:46:43.426Z  INFO 150 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.6.13.Final
2025-05-20T00:46:43.476Z  INFO 150 --- [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2025-05-20T00:46:43.876Z  INFO 150 --- [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2025-05-20T00:46:43.907Z  WARN 150 --- [           main] org.hibernate.orm.deprecation            : HHH90000025: H2Dialect does not need to be specified explicitly using 'hibernate.dialect' (remove the property setting and it will be selected by default)
2025-05-20T00:46:43.923Z  INFO 150 --- [           main] org.hibernate.orm.connections.pooling    : HHH10001005: Database info:
        Database JDBC URL [undefined/unknown]
        Database driver: undefined/unknown
        Database version: 2.1.214
        Autocommit mode: undefined/unknown
        Isolation level: <unknown>
        Minimum pool size: undefined/unknown
        Maximum pool size: undefined/unknown
2025-05-20T00:46:45.066Z  INFO 150 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
Hibernate: drop table if exists employer cascade 
2025-05-20T00:46:45.082Z  INFO 150 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2025-05-20T00:46:45.297Z  INFO 150 --- [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:ironoc_db user=ROOT
2025-05-20T00:46:45.300Z  INFO 150 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
Hibernate: drop table if exists person cascade 
Hibernate: create table employer (start_year integer, employee_id bigint, employer_id bigint generated by default as identity, employer_name varchar(255), title varchar(255), primary key (employer_id))
Hibernate: create table person (age integer not null check ((age<=90) and (age>=1)), title varchar(5) not null, id bigint generated by default as identity, first_name varchar(30) not null, surname varchar(30) not null, primary key (id))
Hibernate: alter table if exists employer add constraint FKncw2hjus3cwslwhelmj9kryiu foreign key (employee_id) references person
2025-05-20T00:46:45.363Z  INFO 150 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2025-05-20T00:46:46.122Z  WARN 150 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2025-05-20T00:46:46.149Z  INFO 150 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page template: index
2025-05-20T00:46:46.810Z  INFO 150 --- [           main] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:ironoc_db'
2025-05-20T00:46:47.022Z  INFO 150 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2025-05-20T00:46:47.040Z  INFO 150 --- [           main] com.ironoc.db.App                        : Started App in 9.124 seconds (process running for 9.703)
.
.
.
2025-05-20T00:49:03.513Z  INFO 150 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2025-05-20T00:49:03.513Z  INFO 150 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2025-05-20T00:49:03.519Z  INFO 150 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 6 ms
2025-05-20T00:49:03.613Z  INFO 150 --- [nio-8080-exec-1] c.ironoc.db.controller.PersonController  : Entering personController.home: map={applicationVersion=Version: 6.2.2}
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
Hibernate: select e1_0.employee_id,e1_0.employer_id,e1_0.employer_name,e1_0.start_year,e1_0.title from employer e1_0 where e1_0.employee_id=?
Hibernate: select e1_0.employee_id,e1_0.employer_id,e1_0.employer_name,e1_0.start_year,e1_0.title from employer e1_0 where e1_0.employee_id=?
Hibernate: select e1_0.employee_id,e1_0.employer_id,e1_0.employer_name,e1_0.start_year,e1_0.title from employer e1_0 where e1_0.employee_id=?
2025-05-20T00:49:12.061Z  INFO 150 --- [nio-8080-exec-5] c.ironoc.db.controller.PersonController  : Entering personController.deletePersonBySurname: map={applicationVersion=Version: 6.2.2}, id=1000
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title,e1_0.employee_id,e1_0.employer_id,e1_0.employer_name,e1_0.start_year,e1_0.title from person p1_0 left join employer e1_0 on p1_0.id=e1_0.employee_id where p1_0.id=?
Hibernate: delete from employer where employer_id=?
Hibernate: delete from employer where employer_id=?
Hibernate: delete from person where id=?
2025-05-20T00:49:12.197Z  INFO 150 --- [nio-8080-exec-6] c.ironoc.db.controller.PersonController  : Entering personController.home: map={applicationVersion=Version: 6.2.2}
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
Hibernate: select e1_0.employee_id,e1_0.employer_id,e1_0.employer_name,e1_0.start_year,e1_0.title from employer e1_0 where e1_0.employee_id=?
Hibernate: select e1_0.employee_id,e1_0.employer_id,e1_0.employer_name,e1_0.start_year,e1_0.title from employer e1_0 where e1_0.employee_id=?
2025-05-20T00:49:28.488Z  INFO 150 --- [nio-8080-exec-7] c.ironoc.db.controller.PersonController  : Entering personController.addPerson: map={applicationVersion=Version: 6.2.2, person=Person(id=null, title=Mr , firstName=Conor, surname=Heffron, age=null, employers=null), org.springframework.validation.BindingResult.person=org.springframework.validation.BeanPropertyBindingResult: 1 errors
Field error in object 'person' on field 'age': rejected value [null]; codes [NotNull.person.age,NotNull.age,NotNull.java.lang.Integer,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [person.age,age]; arguments []; default message [age]]; default message [Age is not defined.]}, person=Person(id=null, title=Mr , firstName=Conor, surname=Heffron, age=null, employers=null)
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
Hibernate: select e1_0.employee_id,e1_0.employer_id,e1_0.employer_name,e1_0.start_year,e1_0.title from employer e1_0 where e1_0.employee_id=?
Hibernate: select e1_0.employee_id,e1_0.employer_id,e1_0.employer_name,e1_0.start_year,e1_0.title from employer e1_0 where e1_0.employee_id=?
2025-05-20T00:49:30.888Z  INFO 150 --- [nio-8080-exec-8] c.ironoc.db.controller.PersonController  : Entering personController.addPerson: map={applicationVersion=Version: 6.2.2, person=Person(id=null, title=Mr , firstName=Conor, surname=Heffron, age=99, employers=null), org.springframework.validation.BindingResult.person=org.springframework.validation.BeanPropertyBindingResult: 1 errors
Field error in object 'person' on field 'age': rejected value [99]; codes [Max.person.age,Max.age,Max.java.lang.Integer,Max]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [person.age,age]; arguments []; default message [age],90]; default message [Age is greater than 90.]}, person=Person(id=null, title=Mr , firstName=Conor, surname=Heffron, age=99, employers=null)
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
Hibernate: select e1_0.employee_id,e1_0.employer_id,e1_0.employer_name,e1_0.start_year,e1_0.title from employer e1_0 where e1_0.employee_id=?
Hibernate: select e1_0.employee_id,e1_0.employer_id,e1_0.employer_name,e1_0.start_year,e1_0.title from employer e1_0 where e1_0.employee_id=?
2025-05-20T00:49:36.350Z  INFO 150 --- [nio-8080-exec-9] c.ironoc.db.controller.PersonController  : Entering personController.addPerson: map={applicationVersion=Version: 6.2.2, person=Person(id=null, title=Mr , firstName=Conor, surname=Heffron, age=23, employers=null), org.springframework.validation.BindingResult.person=org.springframework.validation.BeanPropertyBindingResult: 0 errors}, person=Person(id=null, title=Mr , firstName=Conor, surname=Heffron, age=23, employers=null)
Hibernate: insert into person (age,first_name,surname,title,id) values (?,?,?,?,default)
2025-05-20T00:49:36.394Z  INFO 150 --- [io-8080-exec-10] c.ironoc.db.controller.PersonController  : Entering personController.home: map={applicationVersion=Version: 6.2.2}
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
Hibernate: select e1_0.employee_id,e1_0.employer_id,e1_0.employer_name,e1_0.start_year,e1_0.title from employer e1_0 where e1_0.employee_id=?
Hibernate: select e1_0.employee_id,e1_0.employer_id,e1_0.employer_name,e1_0.start_year,e1_0.title from employer e1_0 where e1_0.employee_id=?
Hibernate: select e1_0.employee_id,e1_0.employer_id,e1_0.employer_name,e1_0.start_year,e1_0.title from employer e1_0 where e1_0.employee_id=?
```  

![minikube-dash-logs](./screenshots/minikube-dash-logs.png?raw=true "ironoc-db kube logs viewer")

![deployment](./screenshots/deployment.png?raw=true "ironoc-db local k8s Deployment")

```shell
% minikube delete
🔥  Deleting "minikube" in docker ...
🔥  Deleting container "minikube" ...
🔥  Removing /Users/conorheffron/.minikube/machines/minikube ...
💀  Removed all traces of the "minikube" cluster.
```

## Alternatively, Docker Desktop is good if you prefer to not use the terminal/command line (CLI)
![docker-desktop-containers](./screenshots/docker-desktop-containers.png?raw=true "Docker Desktop containers")

## Can tail the logs by scrolling within the container logs:
![docker-desktop-ironoc-db-logs](./screenshots/docker-desktop-ironoc-db-logs.png?raw=true "Docker Desktop ironoc-db logs")

## Screenshot Home
![Home](./screenshots/DBManager.png?raw=true "Home Page")

## Screenshot Form Validation Error for Add Person Call
![ui-form-validation](./screenshots/ui-form-validation.png?raw=true "UI form validation")

## Screenshot Form Validation Error for Edit Operation
![ui-edit-validation](./screenshots/ui-edit-validation.png?raw=true "UI Edit validation")


