![GitHub Sponsor](https://img.shields.io/github/sponsors/cph35?label=Sponsor&logo=GitHub)

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
```shell  
%   minikube start --driver=docker       
😄  minikube v1.34.0 on Darwin 15.1.1
❗  Both driver=docker and vm-driver=virtualbox have been set.

    Since vm-driver is deprecated, minikube will default to driver=docker.

    If vm-driver is set in the global config, please run "minikube config unset vm-driver" to resolve this warning.
                        
✨  Using the docker driver based on user configuration

💣  Exiting due to PROVIDER_DOCKER_NOT_RUNNING: "docker version --format <no value>-<no value>:<no value>" exit status 1: Cannot connect to the Docker daemon at unix:///Users/conorheffron/.docker/run/docker.sock. Is the docker daemon running?
💡  Suggestion: Start the Docker service
📘  Documentation: https://minikube.sigs.k8s.io/docs/drivers/docker/

%   minikube start --driver=docker
😄  minikube v1.34.0 on Darwin 15.1.1
❗  Both driver=docker and vm-driver=virtualbox have been set.

    Since vm-driver is deprecated, minikube will default to driver=docker.

    If vm-driver is set in the global config, please run "minikube config unset vm-driver" to resolve this warning.
                        
✨  Using the docker driver based on user configuration
📌  Using Docker Desktop driver with root privileges
👍  Starting "minikube" primary control-plane node in "minikube" cluster
🚜  Pulling base image v0.0.45 ...
🔥  Creating docker container (CPUs=2, Memory=4000MB) ...
🐳  Preparing Kubernetes v1.31.0 on Docker 27.2.0 ...
    ▪ Generating certificates and keys ...
    ▪ Booting up control plane ...
    ▪ Configuring RBAC rules ...
🔗  Configuring bridge CNI (Container Networking Interface) ...
🔎  Verifying Kubernetes components...
    ▪ Using image gcr.io/k8s-minikube/storage-provisioner:v5
🌟  Enabled addons: storage-provisioner, default-storageclass
🏄  Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default


%   kubectl cluster-info 
Kubernetes control plane is running at https://127.0.0.1:51600
CoreDNS is running at https://127.0.0.1:51600/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy

To further debug and diagnose cluster problems, use 'kubectl cluster-info dump'.


%   minikube dashboard 
🔌  Enabling dashboard ...
    ▪ Using image docker.io/kubernetesui/dashboard:v2.7.0
    ▪ Using image docker.io/kubernetesui/metrics-scraper:v1.0.8
💡  Some dashboard features require the metrics-server addon. To enable all features please run:

        minikube addons enable metrics-server

🤔  Verifying dashboard health ...
🚀  Launching proxy ...
🤔  Verifying proxy health ...
🎉  Opening http://127.0.0.1:52168/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/ in your default browser...
```
- Then change namespace in browser after creation of 'ironoc-db' namespace.

![minikube-dash](./screenshots/minikube-dash.png?raw=true "Minikube Dashboard")
```shell
%   docker images
REPOSITORY                    TAG       IMAGE ID       CREATED        SIZE
gcr.io/k8s-minikube/kicbase   v0.0.45   e7c9bc3bc515   3 months ago   1.81GB
gcr.io/k8s-minikube/kicbase   <none>    81df28859520   3 months ago   1.81GB


%   docker image build -t ironoc-db .
[+] Building 44.1s (9/9) FINISHED                                                                                                                                                                                                                                                           docker:desktop-linux
 => [internal] load build definition from Dockerfile                                                                                                                                                                                                                                                        0.0s
 => => transferring dockerfile: 266B                                                                                                                                                                                                                                                                        0.0s
 => [internal] load metadata for docker.io/library/gradle:8.11.1-jdk23-alpine                                                                                                                                                                                                                               1.3s
 => [auth] library/gradle:pull token for registry-1.docker.io                                                                                                                                                                                                                                               0.0s
 => [internal] load .dockerignore                                                                                                                                                                                                                                                                           0.0s
 => => transferring context: 2B                                                                                                                                                                                                                                                                             0.0s
 => [internal] load build context                                                                                                                                                                                                                                                                           2.4s
 => => transferring context: 105.79MB                                                                                                                                                                                                                                                                       2.3s
 => CACHED [1/4] FROM docker.io/library/gradle:8.11.1-jdk23-alpine@sha256:a61858da62eeb4ba9a10e1a188fff2303ebcde278d629d9e2161adeca8455543                                                                                                                                                                  0.0s
 => => resolve docker.io/library/gradle:8.11.1-jdk23-alpine@sha256:a61858da62eeb4ba9a10e1a188fff2303ebcde278d629d9e2161adeca8455543                                                                                                                                                                         0.0s
 => [2/4] COPY . /home/gradle                                                                                                                                                                                                                                                                               2.1s
 => [3/4] RUN export LD_BIND_NOW=1                                                                                                                                                                                                                                                                          0.3s
 => CANCELED [4/4] RUN gradle build                                                                                                                                                                                                                                                                        37.8s
ERROR: failed to solve: Canceled: context canceled                                                                                                                                                                                                                                                               


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
ironoc-db-app-deployment-6c566784bc-xvt6c   1/1     Running   0          10s


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
ironoc-db-app-deployment   NodePort   10.98.99.20   <none>        8080:31200/TCP   7s


%   minikube service ironoc-db-app-deployment --url --namespace=ironoc-db-ns
http://127.0.0.1:52851
❗  Because you are using a Docker driver on darwin, the terminal needs to be open to run it.
```
- Open a new terminal tab & follow the logs
```shell
%   kubectl get pods --namespace=ironoc-db-ns
NAME                                        READY   STATUS    RESTARTS   AGE
ironoc-db-app-deployment-6c566784bc-xvt6c   1/1     Running   0          2m54s


%   kubectl logs ironoc-db-app-deployment-6c566784bc-xvt6c -f --namespace=ironoc-db-ns
Starting a Gradle Daemon, 1 incompatible and 1 stopped Daemons could not be reused, use --status for details
> Task :compileJava UP-TO-DATE
> Task :processResources UP-TO-DATE
> Task :classes UP-TO-DATE
> Task :resolveMainClassName UP-TO-DATE

> Task :bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | _| | '_ \/ _ | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.4.0)

2024-12-13T18:07:08.237Z  INFO 148 --- [           main] com.ironoc.db.App                        : Starting App using Java 23.0.1 with PID 148 (/home/gradle/build/classes/java/main started by root in /home/gradle)
2024-12-13T18:07:08.242Z  INFO 148 --- [           main] com.ironoc.db.App                        : The following 1 profile is active: "h2"
2024-12-13T18:07:10.111Z  INFO 148 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2024-12-13T18:07:10.206Z  INFO 148 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 75 ms. Found 1 JPA repository interface.
2024-12-13T18:07:11.711Z  INFO 148 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2024-12-13T18:07:11.742Z  INFO 148 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-12-13T18:07:11.742Z  INFO 148 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/11.0.1]
2024-12-13T18:07:12.349Z  INFO 148 --- [           main] org.apache.jasper.servlet.TldScanner     : At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
2024-12-13T18:07:12.446Z  INFO 148 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-12-13T18:07:12.463Z  INFO 148 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 4038 ms
2024-12-13T18:07:12.527Z  INFO 148 --- [           main] c.i.db.service.GoogleCloudClientImpl     : Entering GoogleCloudClient.getSecret for secretVersion=projects/************/secrets/MY_SQL_PASSWORD/versions/1
2024-12-13T18:07:13.787Z  INFO 148 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2024-12-13T18:07:14.484Z  INFO 148 --- [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:ironoc_db user=ROOT
2024-12-13T18:07:14.488Z  INFO 148 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2024-12-13T18:07:14.533Z  INFO 148 --- [           main] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:ironoc_db'
2024-12-13T18:07:14.852Z  INFO 148 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2024-12-13T18:07:15.000Z  INFO 148 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.6.2.Final
2024-12-13T18:07:15.107Z  INFO 148 --- [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2024-12-13T18:07:15.710Z  INFO 148 --- [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2024-12-13T18:07:15.757Z  WARN 148 --- [           main] org.hibernate.orm.deprecation            : HHH90000025: H2Dialect does not need to be specified explicitly using 'hibernate.dialect' (remove the property setting and it will be selected by default)
2024-12-13T18:07:15.777Z  INFO 148 --- [           main] org.hibernate.orm.connections.pooling    : HHH10001005: Database info:
        Database JDBC URL [undefined/unknown]
        Database driver: undefined/unknown
        Database version: 2.1.214
        Autocommit mode: undefined/unknown
        Isolation level: <unknown>
        Minimum pool size: undefined/unknown
        Maximum pool size: undefined/unknown
Hibernate: create global temporary table HTE_person(age integer, rn_ integer not null, title varchar(5), id bigint, first_name varchar(30), surname varchar(30), primary key (rn_)) TRANSACTIONAL
2024-12-13T18:07:17.719Z  INFO 148 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
Hibernate: drop table if exists person cascade 
Hibernate: drop sequence if exists person_seq
Hibernate: create sequence person_seq start with 1 increment by 50
Hibernate: create table person (age integer not null check ((age<=90) and (age>=1)), title varchar(5) not null, id bigint not null, first_name varchar(30) not null, surname varchar(30) not null, primary key (id))
2024-12-13T18:07:17.797Z  INFO 148 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2024-12-13T18:07:18.869Z  WARN 148 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2024-12-13T18:07:18.914Z  INFO 148 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page template: index
2024-12-13T18:07:20.132Z  INFO 148 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2024-12-13T18:07:20.147Z  INFO 148 --- [           main] com.ironoc.db.App                        : Started App in 12.67 seconds (process running for 13.407)
2024-12-13T18:08:23.985Z  INFO 148 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2024-12-13T18:08:23.986Z  INFO 148 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2024-12-13T18:08:23.991Z  INFO 148 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 4 ms
2024-12-13T18:08:24.139Z  INFO 148 --- [nio-8080-exec-1] c.ironoc.db.controller.PersonController  : Entering personController.home: map={}
Hibernate: select p1_0.id,p1_0.age,p1_0.first_name,p1_0.surname,p1_0.title from person p1_0
2024-12-13T18:08:43.017Z  INFO 148 --- [nio-8080-exec-5] c.ironoc.db.controller.PersonController  : Entering personController.home: map={}
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


