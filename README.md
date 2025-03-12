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
Java 23, Spring Boot 3, Hibernate, MySQL or H2 databases supported, JSP, Gradle 8.11, 
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

## Login to gcloud project for authentication tokens etc. for save to local workspace
### Accept & allow browser prompts.
### Note: This is for direct app run, need to to do this in container or pod for virtualization.
```
gcloud auth application-default login
```

## Verify credentials (set appropriate svc / user account & project name)
```
gcloud config list
```

## Run 'com.ironoc.db.App.java' directly from IntelliJ (can use localhost for spring.datasource.url) or 
## via CLI (build & spin up docker image, use docker network IP address for test-mysql process):
```
docker image build -t ironoc-db .

docker compose up -d

docker ps

docker logs ironoc-db-web-1 -f

docker compose down
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
[+] Building 122.6s (8/8) FINISHED                                                                                                                                                                                                                                                          docker:desktop-linux
 => [internal] load build definition from Dockerfile                                                                                                                                                                                                                                                        0.0s
 => => transferring dockerfile: 241B                                                                                                                                                                                                                                                                        0.0s
 => [internal] load metadata for docker.io/library/gradle:8.11.1-jdk23-alpine                                                                                                                                                                                                                               0.8s
 => [internal] load .dockerignore                                                                                                                                                                                                                                                                           0.0s
 => => transferring context: 2B                                                                                                                                                                                                                                                                             0.0s
 => [internal] load build context                                                                                                                                                                                                                                                                           0.6s
 => => transferring context: 252.81kB                                                                                                                                                                                                                                                                       0.6s
 => CACHED [1/3] FROM docker.io/library/gradle:8.11.1-jdk23-alpine@sha256:a61858da62eeb4ba9a10e1a188fff2303ebcde278d629d9e2161adeca8455543                                                                                                                                                                  0.0s
 => => resolve docker.io/library/gradle:8.11.1-jdk23-alpine@sha256:a61858da62eeb4ba9a10e1a188fff2303ebcde278d629d9e2161adeca8455543                                                                                                                                                                         0.0s
 => [2/3] COPY . /home/gradle                                                                                                                                                                                                                                                                               0.7s
 => [3/3] RUN gradle build                                                                                                                                                                                                                                                                                 99.6s
 => exporting to image                                                                                                                                                                                                                                                                                     20.7s 
 => => exporting layers                                                                                                                                                                                                                                                                                    15.4s 
 => => exporting manifest sha256:6254bab1642bda1893d83a6c43db990ad3d876bb2c8df4b71d6179f421d48fc9                                                                                                                                                                                                           0.0s 
 => => exporting config sha256:83f14c983f12d2f492c6840136cf94fc2a7947c21fd036ede1a3c661cb7bd061                                                                                                                                                                                                             0.0s 
 => => exporting attestation manifest sha256:a320ea5f81d729d731654902265d715d07faffb99b74b169d9f20855bbb5c38b                                                                                                                                                                                               0.1s 
 => => exporting manifest list sha256:1e287e0f161e9d2c71deb57e06d4c619caa79a68ac2648ada074f3fe94d82fee                                                                                                                                                                                                      0.0s 
 => => naming to docker.io/library/ironoc-db:latest                                                                                                                                                                                                                                                         0.0s
 => => unpacking to docker.io/library/ironoc-db:latest                                                                                                                                                                                                                                                      5.1s

View build details: docker-desktop://dashboard/build/desktop-linux/desktop-linux/py9r2clbl41pqcl6ys5dqskmp

Whats next:
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
> Task :compileJava UP-TO-DATE
> Task :processResources UP-TO-DATE
> Task :classes UP-TO-DATE
> Task :resolveMainClassName UP-TO-DATE

> Task :bootRun

.__ __________                                  ________             __           ___.                            
|__|\______   \  ____    ____    ____    ____   \______ \  _____   _/  |_ _____   \_ |__  _____     ______  ____  
|  | |       _/ /  _ \  /    \  /  _ \ _/ ___\   |    |  \ \__  \  \   __\\__  \   | __ \ \__  \   /  ___/_/ __ \ 
|  | |    |   \(  <_> )|   |  \(  <_> )\  \___   |    `   \ / __ \_ |  |   / __ \_ | \_\ \ / __ \_ \___ \ \  ___/ 
|__| |____|_  / \____/ |___|  / \____/  \___  > /_______  /(____  / |__|  (____  / |___  /(____  //____  > \___  >
            \/              \/              \/          \/      \/             \/      \/      \/      \/      \/ 


2025-02-20T19:35:28.390Z  INFO 143 --- [           main] com.ironoc.db.App                        : Starting App using Java 23.0.1 with PID 143 (/home/gradle/build/classes/java/main started by root in /home/gradle)
2025-02-20T19:35:28.396Z  INFO 143 --- [           main] com.ironoc.db.App                        : The following 1 profile is active: "h2"
2025-02-20T19:35:30.088Z  INFO 143 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2025-02-20T19:35:30.171Z  INFO 143 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 67 ms. Found 1 JPA repository interface.
2025-02-20T19:35:31.099Z  INFO 143 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2025-02-20T19:35:31.125Z  INFO 143 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2025-02-20T19:35:31.126Z  INFO 143 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/11.0.3]
2025-02-20T19:35:31.496Z  INFO 143 --- [           main] org.apache.jasper.servlet.TldScanner     : At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
2025-02-20T19:35:31.504Z  INFO 143 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2025-02-20T19:35:31.506Z  INFO 143 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 2878 ms
2025-02-20T19:35:31.579Z  INFO 143 --- [           main] c.i.db.service.GoogleCloudClientImpl     : Entering GoogleCloudClient.getSecret ****
2025-02-20T19:35:32.290Z  INFO 143 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2025-02-20T19:35:32.374Z  INFO 143 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.6.5.Final
2025-02-20T19:35:32.437Z  INFO 143 --- [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2025-02-20T19:35:32.863Z  INFO 143 --- [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2025-02-20T19:35:32.905Z  WARN 143 --- [           main] org.hibernate.orm.deprecation            : HHH90000025: H2Dialect does not need to be specified explicitly using 'hibernate.dialect' (remove the property setting and it will be selected by default)
2025-02-20T19:35:32.923Z  INFO 143 --- [           main] org.hibernate.orm.connections.pooling    : HHH10001005: Database info:
        Database JDBC URL [undefined/unknown]
        Database driver: undefined/unknown
        Database version: 2.1.214
        Autocommit mode: undefined/unknown
        Isolation level: <unknown>
        Minimum pool size: undefined/unknown
        Maximum pool size: undefined/unknown
2025-02-20T19:35:34.217Z  INFO 143 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2025-02-20T19:35:34.498Z  INFO 143 --- [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:ironoc_db user=ROOT
2025-02-20T19:35:34.502Z  INFO 143 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
Hibernate: create global temporary table HTE_person(age integer, rn_ integer not null, title varchar(5), id bigint, first_name varchar(30), surname varchar(30), primary key (rn_)) TRANSACTIONAL
2025-02-20T19:35:34.855Z  INFO 143 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
Hibernate: drop table if exists person cascade 
Hibernate: drop sequence if exists person_seq
Hibernate: create sequence person_seq start with 1 increment by 50
Hibernate: create table person (age integer not null check ((age<=90) and (age>=1)), title varchar(5) not null, id bigint not null, first_name varchar(30) not null, surname varchar(30) not null, primary key (id))
2025-02-20T19:35:34.979Z  INFO 143 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2025-02-20T19:35:35.829Z  WARN 143 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2025-02-20T19:35:35.875Z  INFO 143 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page template: index
2025-02-20T19:35:36.600Z  INFO 143 --- [           main] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:ironoc_db'
2025-02-20T19:35:36.870Z  INFO 143 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2025-02-20T19:35:36.910Z  INFO 143 --- [           main] com.ironoc.db.App                        : Started App in 10.139 seconds (process running for 11.365)
2025-02-20T19:36:02.454Z  INFO 143 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2025-02-20T19:36:02.455Z  INFO 143 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2025-02-20T19:36:02.457Z  INFO 143 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 2 ms
2025-02-20T19:36:02.520Z  INFO 143 --- [nio-8080-exec-1] c.ironoc.db.controller.PersonController  : Entering personController.home: map={}
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
2025-02-20T19:38:20.100Z  INFO 143 --- [nio-8080-exec-7] c.ironoc.db.controller.PersonController  : Entering personController.deletePersonBySurname: map={}, id=1001
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0 where p1_0.id=?
Hibernate: delete from person where id=?
2025-02-20T19:38:20.287Z  INFO 143 --- [nio-8080-exec-9] c.ironoc.db.controller.PersonController  : Entering personController.home: map={}
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
2025-02-20T19:38:21.570Z  INFO 143 --- [nio-8080-exec-3] c.ironoc.db.controller.PersonController  : Entering personController.deletePersonBySurname: map={}, id=1002
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0 where p1_0.id=?
Hibernate: delete from person where id=?
2025-02-20T19:38:21.585Z  INFO 143 --- [nio-8080-exec-4] c.ironoc.db.controller.PersonController  : Entering personController.home: map={}
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
2025-02-20T19:38:23.162Z  INFO 143 --- [nio-8080-exec-5] c.ironoc.db.controller.PersonController  : Entering personController.deletePersonBySurname: map={}, id=1003
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0 where p1_0.id=?
Hibernate: delete from person where id=?
2025-02-20T19:38:23.179Z  INFO 143 --- [nio-8080-exec-8] c.ironoc.db.controller.PersonController  : Entering personController.home: map={}
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
2025-02-20T19:38:32.353Z  INFO 143 --- [nio-8080-exec-6] c.ironoc.db.controller.PersonController  : Entering personController.addPerson: map={person=Person(id=null, title=Ms, firstName=Halle, surname=Movie, age=4747), org.springframework.validation.BindingResult.person=org.springframework.validation.BeanPropertyBindingResult: 1 errors
Field error in object 'person' on field 'age': rejected value [4747]; codes [Max.person.age,Max.age,Max.java.lang.Integer,Max]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [person.age,age]; arguments []; default message [age],90]; default message [Age is greater than 90.]}, person=Person(id=null, title=Ms, firstName=Halle, surname=Movie, age=4747)
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
2025-02-20T19:38:34.940Z  INFO 143 --- [nio-8080-exec-7] c.ironoc.db.controller.PersonController  : Entering personController.addPerson: map={person=Person(id=null, title=Ms, firstName=Halle, surname=Movie, age=47), org.springframework.validation.BindingResult.person=org.springframework.validation.BeanPropertyBindingResult: 0 errors}, person=Person(id=null, title=Ms, firstName=Halle, surname=Movie, age=47)
Hibernate: select next value for person_seq
Hibernate: insert into person (age,first_name,surname,title,id) values (?,?,?,?,?)
2025-02-20T19:38:35.114Z  INFO 143 --- [nio-8080-exec-9] c.ironoc.db.controller.PersonController  : Entering personController.home: map={}
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
2025-02-20T19:38:37.202Z  INFO 143 --- [io-8080-exec-10] c.ironoc.db.controller.PersonController  : Entering personController.showEditView: ID=1, model={}
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0 where p1_0.id=?
2025-02-20T19:38:41.423Z  INFO 143 --- [nio-8080-exec-1] c.ironoc.db.controller.PersonController  : Entering personController.updatePerson: ID=1, person=Person(id=1, title=Ms, firstName=Halle, surname=Movie, age=52)
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0 where p1_0.id=?
Hibernate: update person set age=?,first_name=?,surname=?,title=? where id=?
2025-02-20T19:38:41.466Z  INFO 143 --- [nio-8080-exec-2] c.ironoc.db.controller.PersonController  : Entering personController.home: map={}
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
2025-02-20T19:38:43.210Z  INFO 143 --- [nio-8080-exec-3] c.ironoc.db.controller.PersonController  : Entering personController.deletePersonBySurname: map={}, id=1
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0 where p1_0.id=?
Hibernate: delete from person where id=?
2025-02-20T19:38:43.227Z  INFO 143 --- [nio-8080-exec-4] c.ironoc.db.controller.PersonController  : Entering personController.home: map={}
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
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


