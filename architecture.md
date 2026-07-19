# iRonoc-DB Architecture & System Design Documentation

This document provides a highly detailed overview of the system design, layered Java backend, Thymeleaf-based frontend, database integrations, and interactive User Experience (UX) flows of **iRonoc-DB**.

---

## 1. High-Level System Architecture & Deployment Design

iRonoc-DB is a containerized, cloud-ready application built on **Java 25** and **Spring Boot 4**. It supports multi-environment run profiles, permitting seamless operations inside local developer environments (via Docker Compose or Minikube) as well as automated production container-orchestrated platforms like Google Kubernetes Engine (GKE).

### 1.1 Detailed System Design Topology

The following diagram represents the physical, network, and software container topology of the application across both local development environments and cloud-orchestrated clusters:

```mermaid
graph TD
    subgraph Client Space [Client Tier]
        User([User's Browser]) <-->|Local: HTTP 8080<br/>K8s: NodePort/LoadBalancer| NetworkGate[Ingress Network Gateway]
    end

    subgraph ContainerRuntime [Docker Compose Environment / K8s Pod]
        direction TB
        subgraph AppContainer [Spring Boot App Container]
            Tomcat[Embedded Tomcat Web Server] <-->|Incoming Requests| MVC[Spring Boot MVC DispatcherServlet]
            MVC <-->|Spring Beans Dependency Injection| SpringContext[Spring Application Context]
            
            subgraph JVM_Memory [JVM Heap Runtime]
                SpringContext <-->|On-Heap Caching| Cache[ConcurrentMapCacheManager]
                SpringContext <-->|Hikari Connection Pool| Hikari[HikariCP DataSource]
                SpringContext <-->|GCP SDK Service Client| SecretClient[SecretManagerServiceClient]
            end
        end

        subgraph DbContainer [Database Environment]
            MySQL[(MySQL Container<br/>Port 3306 / 3307)]
            H2[(In-Memory H2 Database<br/>Inside App JVM Heap)]
        end
    end

    subgraph GoogleCloud [Google Cloud Platform Services]
        GCP_SecManager[Google Cloud Secret Manager]
    end

    %% Client and external routing
    NetworkGate <-->|Routes to Container Port 8080| Tomcat
    
    %% GCP Integrations
    SecretClient -.->|API Call via Application Default Credentials| GCP_SecManager
    
    %% Database routing depending on Spring Active Profiles
    Hikari <-->|ActiveProfile: mysql / JDBC| MySQL
    Hikari <-->|ActiveProfile: h2 / JDBC| H2
```

### 1.2 Deployment & Environment Run Modes

The system operates under two primary profiles configured in the Maven/Gradle build system and Spring Boot's application properties:

1. **Development/Local Memory Mode (`h2` profile):**
   - **Database:** An in-memory **H2 Database** instance (`jdbc:h2:mem:ironoc_db`).
   - **Credentials Management:** Instead of local hardcoding, the database password is dynamically retrieved from **Google Cloud Secret Manager** at startup using a custom client wrapper. This enforces strict credential protection even in non-production local profiles.
   - **Console:** The H2 Web Console is exposed under `/h2-console` for interactive runtime debugging.

2. **Standard Relational Mode (`mysql` profile):**
   - **Database:** A **MySQL database** running inside a separate Docker Compose container or hosted externally (e.g., Google Cloud SQL).
   - **Credentials Management:** Pulls connection parameters (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`) directly from environment variables.

---

## 2. Layered Java Backend Overview

The backend conforms to the classical **Layered (3-Tier) Architecture** to ensure clean separation of concerns, high testability, and structural integrity.

```mermaid
graph TD
    subgraph PresentationTier [Presentation / Web Layer]
        Controllers[Spring Boot MVC Controllers]
        DTOs[Data Transfer Objects]
        Mappers[Entity-DTO Mappers]
    end

    subgraph BusinessTier [Business Logic Layer]
        Services[Spring Services]
        Cache[Spring Cache Abstraction]
        GCPSvc[GCP Secret Integration]
    end

    subgraph IntegrationTier [Data Access Layer]
        DAOs[Spring Data JPA Repositories]
        Entities[JPA Hibernate Entities]
    end

    Controllers <--> Services
    Services <--> DAOs
    Controllers -.-> DTOs
    Mappers -.-> DTOs
    Mappers -.-> Entities
```

### 2.1 Component Breakdown

#### A. Web / Controller Layer
- **MVC Controller Routing:** Classes like `PersonController` and `EmployerController` map client HTTP requests to model-view resolutions. They act as traffic directors, orchestrating request flow.
- **Global Controller Advice:** `VersionController` is configured with `@ControllerAdvice` and a global `@ModelAttribute("applicationVersion")` model provider. This automatically injects the parsed compilation version into all dynamic templates without requiring repetitive attribute loading across other endpoints.
- **Custom Error Handling:** `CustomErrorController` implements Spring Boot's `ErrorController` to capture unhandled exceptions or 404s, logging diagnostic attributes (HTTP status, exception messages, request URI) and routing requests cleanly to custom error UI (`error404.html`).

#### B. Service & Business Logic Layer
- **Interface Segregation:** Services are structured with clean contracts (`PersonService`, `EmployerService`) and explicit implementations (`PersonServiceImpl`, `EmployerServiceImpl`).
- **External Integration Services:** `GoogleCloudClient` leverages the official Google Cloud SDK client `SecretManagerServiceClient` to fetch the H2 instance password dynamically from cloud secrets vaults at runtime.

#### C. Data Access & Persistence Layer (DAO)
- **Spring Data JPA Repositories:** Repository interfaces (`PersonDao`, `EmployerDao`) inherit from standard interfaces (`JpaRepository`, `CrudRepository`).
- **Database Transactions:** Crucial write or read-update operations are enclosed in `@Transactional` annotations to enforce ACID transactions.

#### D. Boundary Model Strategy: Entities vs. DTOs
To prevent the leakage of Hibernate state, circular mapping, or database constraints into the web presentation tier, iRonoc-DB strictly enforces a DTO/Entity boundary:
- **Entities (`Person`, `Employer`):** Map directly to SQL relational structures via JPA annotations (`@Entity`, `@Table`, `@ManyToOne`, `@OneToMany`). They handle lifecycle cascade behaviors.
- **DTOs (`PersonDto`, `EmployerDto`):** Flat Java objects containing request-bound properties, fully isolated from JPA tracking.
- **Mappers (`PersonMapper`):** A custom Spring bean that surgically maps back and forth between DTOs and Entities during inbound requests or outbound model loads.

#### E. Strict Form Validation
Input validation uses standard **Jakarta Bean Validation** constraints configured directly on the DTO properties. 

Example attributes:
- `@NotEmpty` and `@Size(min = 3, max = 30)` for text parameters like `firstName` and `surname`.
- `@Min` and `@Max` constraints for numeric properties such as `age` and `startYear`.
- Controller endpoints enforce validation via the `@Valid` trigger. If the bound inputs violate a constraint, Spring's `BindingResult` gathers the failure details, bypasses database interactions, and pushes the errors back to the UI view where they are rendered.

#### F. High-Performance Spring Caching
The application includes on-heap cache management to avoid expensive query execution for repetitive lookups.
- **Configuration:** The `ConcurrentMapCacheManager` is initialized as a bean inside `IronocDbConfig`.
- **Caching Annotations:**
  - `@Cacheable`: Applied to heavy-read queries like `getAllPersons()`, `findPersonBySurname(String surname)` and `findPersonById(Long id)`.
  - `@CacheEvict`: Configured under `@Caching` arrays on state-modifying actions (such as `addPerson`, `deletePersonBySurname`, `deletePersonById`) to flush stagnant cached results and maintain data consistency.
  - **Dynamic Pagination Isolation:** Paginated queries (`getPersonsPage`) are intentionally isolated from caching to guarantee real-time data accuracy during client sorting or record shifts.

---

## 3. Thymeleaf Frontend & UX Overview

iRonoc-DB leverages a dynamic server-side rendering architecture. Web pages are constructed dynamically in memory on the Spring container using the **Thymeleaf Template Engine** before the final HTML is streamed to the user's browser.

### 3.1 Architecture of Thymeleaf Templates
The frontend UI is designed with a high level of modularity and layout reuse:

- **Layout Composition:** Templates are dynamically composed using Thymeleaf's fragment expression syntax (`th:replace`). `index.html` serves as a master container, injecting the reusable header navbar, the add form panel, and the list component.
- **Interactive Forms:** Forms utilize Spring-Thymeleaf binding features like `th:object` (mapping a DTO model) and `th:field` (automatically binding form controls to model fields).
- **Inline Error Feedback:** Fields check for validation failures using `th:if="${#fields.hasErrors('fieldName')}"` and render the constraint's custom error messages in red helper text directly below the target inputs (`th:errors="*{fieldName}"`).
- **Pagination Controls:** The `employee-list.html` component contains conditional pagination buttons powered by calculation utilities (`#numbers.sequence(0, totalPages - 1)`), highlighting active pages and disabling edge controls via standard conditional attributes (`th:classappend`).

### 3.2 Static Resources & UI Libraries
- **Styles & Layout:** The system utilizes **Bootstrap 4** (pulled via CDN) for responsive design and structural spacing, layered with a custom stylesheet (`/style/main.css`) to define customized elements like version badges and page-specific branding.
- **Icons:** **Font Awesome 5** is used to render icons for UI actions like edit, delete, and add buttons.
- **Static Content Handling:** Images (e.g., `/img/robot-logo.png`) and application banners are resolved via Spring Boot's automatic static resource handler, serving files directly from the `/static` folder.

---

## 4. User Experience (UX) Flow & State Transitions

The frontend user experience consists of navigating through employee lists, modifying worker profiles, managing granular job history records, and receiving immediate feedback when input parameters are invalid.

### 4.1 Navigation & State Machine Diagram

The diagram below tracks the states a user transitions through while working with the application, highlighting URLs, methods, and triggers:

```mermaid
stateDiagram-v2
    [*] --> Dashboard : Access URL http://localhost:8080/ (GET)
    
    state Dashboard {
        [*] --> ViewEmployees
        ViewEmployees --> FilterEmployees : Input Surname & Click "Filter"
        FilterEmployees --> ViewEmployees : Click "Clear" Filter
        ViewEmployees --> Paginate : Click Page 1, 2, ...
    }
    
    Dashboard --> EditPersonView : Click "Edit" Button on Employee Row (GET /edit/{id})
    state EditPersonView {
        [*] --> EditPersonForm
        EditPersonForm --> Dashboard : Cancel / Click Navbar brand
        EditPersonForm --> Dashboard : Submit Valid Changes (POST /update/{id})
        EditPersonForm --> EditPersonForm : Submit Invalid (Renders Inline Errors)
    }

    Dashboard --> DeleteConfirmation : Click "Trash" Icon (DELETE /delete/{id})
    DeleteConfirmation --> Dashboard : Successful Delete & Redirect "/"

    Dashboard --> JobHistoryView : Click "Manage Job History" (GET /job-history/{personId})
    state JobHistoryView {
        [*] --> ViewJobHistoryList
        ViewJobHistoryList --> AddJobHistoryForm : Fill Title, Employer, Year & Submit
        AddJobHistoryForm --> ViewJobHistoryList : Valid Submission (Refresh GET)
        AddJobHistoryForm --> ViewJobHistoryList : Invalid Submission (Render Validation Errors)
        ViewJobHistoryList --> DeleteJobRecord : Click "Trash" Icon (DELETE /job-history/delete/{id})
        DeleteJobRecord --> ViewJobHistoryList : Successful Delete & Redirect
    }

    JobHistoryView --> EditJobHistoryView : Click "Edit" Button on Job row (GET /job-history/edit/{id})
    state EditJobHistoryView {
        [*] --> EditJobForm
        EditJobForm --> JobHistoryView : Submit Valid Changes (POST /job-history/update/{id})
        EditJobForm --> EditJobForm : Submit Invalid (Renders Inline Errors)
    }

    Dashboard --> ErrorPage : Enter Invalid URL or Route Error
    JobHistoryView --> ErrorPage : Enter Invalid Person ID
    ErrorPage --> Dashboard : Click Navbar Logo / Redirect
```

### 4.2 Dynamic Form Validation and Error Feedback Loop

To guarantee validation accuracy without interrupting the user's flow, iRonoc-DB implements a feedback mechanism where state-violating submissions are captured, parsed, and rendered inline without persisting raw data in the database:

```mermaid
sequenceDiagram
    autonumber
    actor User as User Browser
    participant HTML as Form Template (add-employee.html)
    participant Ctrl as PersonController
    participant Model as Spring MVC ModelMap
    participant Repo as Database / Repository

    User->>HTML: Fills Form (e.g., age=150, firstName="Bo")
    User->>HTML: Clicks "Add Employee"
    HTML->>Ctrl: POST /add with Form Data (PersonDto)
    Note over Ctrl: Spring validation kicks in<br/>Interprets @Min(1), @Max(90) on Age<br/>Interprets @Size(min=3) on First Name
    
    rect rgb(255, 230, 230)
        Note over Ctrl: Validation Fails!<br/>BindingResult.hasErrors() is TRUE
        Ctrl->>Ctrl: Retrieve standard pagination content
        Ctrl->>Model: Put invalid PersonDto back in Model as "person"
        Ctrl->>Model: Put field error context in Model
        Ctrl-->>HTML: Resolve view "index.html" (No database insert!)
    end
    
    Note over HTML: Thymeleaf engine processes errors:<br/>th:field="*{firstName}" retains entered "Bo"<br/>th:errors="*{firstName}" inserts error label
    HTML-->>User: Renders red error message:<br/>"First Name should be between 3-30 characters."
```

---

## 5. Data Flows & Lifecycle Sequences

### 5.1 Create Record Flow (Adding an Employee)

The sequence diagram below displays the lifecycle of an inbound POST request designed to create a new `Person` record when validation successfully passes:

```mermaid
sequenceDiagram
    autonumber
    actor User as User Browser
    participant Controller as PersonController
    participant Service as PersonServiceImpl
    participant Dao as PersonDao
    participant Cache as ConcurrentMapCacheManager
    participant DB as Database (H2 / MySQL)
    
    User->>Controller: POST /add (Form Fields)
    Note over Controller: Bind & Validate inbound PersonDto using @Valid
    alt Validation Fails (BindingResult hasErrors)
        Controller-->>User: Render "index.html" with dynamic validation messages
    else Validation Passes
        Controller->>Controller: Map PersonDto to Person Entity (using PersonMapper)
        Controller->>Service: addPerson(Person)
        rect rgb(245, 245, 245)
            Note over Service: Trigger Cache Eviction
            Service->>Cache: Clear "persons", "personsBySurname", "personById" caches
        end
        Service->>Dao: save(Person)
        Dao->>DB: INSERT INTO person ...
        DB-->>Dao: Persisted Person Entity
        Dao-->>Service: Return Person
        Service-->>Controller: Return true
        Controller-->>User: Redirect to "/" (302 Redirect)
        Note over User: Browser triggers GET / to load updated view
    end
```

### 5.2 Read Record Flow (Paginated List Retrieval)

The sequence diagram below shows how the default paginated view is served to the client, illustrating the bypass of cache limits for list layouts:

```mermaid
sequenceDiagram
    autonumber
    actor User as User Browser
    participant Controller as PersonController
    participant Service as PersonServiceImpl
    participant Dao as PersonDao
    participant DB as Database (H2 / MySQL)
    
    User->>Controller: GET /?page=0
    Controller->>Service: getPersonsPage(page=0, size=5)
    Note over Service: Bypasses Caching (ensures live paginated data)
    Service->>Dao: findAll(PageRequest.of(0, 5))
    Dao->>DB: SELECT * FROM person LIMIT 5
    DB-->>Dao: ResultSet (Eagerly loads related Employers)
    Dao-->>Service: Page<Person>
    Service-->>Controller: Page<Person>
    Controller->>Controller: Attach personsList, pagination indexes, & new PersonDto to Model
    Controller-->>User: Process index.html (Thymeleaf engine) & return final HTML
```

---

## 6. Detailed Relational Schema & Entity Relationships

The core database schema manages two tables: `person` and `employer`. These represent a strict **One-to-Many Relationship** where an employee can have multiple historical job experience entries in the database.

### 6.1 Entity Relationship Diagram

```mermaid
erDiagram
    PERSON {
        Long id PK "id (BIGINT, IDENTITY)"
        String title "title (VARCHAR, NotEmpty, Size 2-5)"
        String firstName "first_name (VARCHAR, NotEmpty, Size 3-30)"
        String surname "surname (VARCHAR, NotEmpty, Size 3-30)"
        Integer age "age (INT, NotNull, Min 1, Max 90)"
    }
    
    EMPLOYER {
        Long employerId PK "employer_id (BIGINT, IDENTITY)"
        Long employee_id FK "employee_id (BIGINT, References PERSON.id)"
        String title "title (VARCHAR, NotEmpty, Size 2-45)"
        String employerName "employer_name (VARCHAR, NotEmpty, Size 2-45)"
        Integer startYear "start_year (INT, NotNull, Min 1900, Max 2100)"
    }
    
    PERSON ||--o{ EMPLOYER : "has history (1 to many)"
```

### 6.2 Relationship Lifecycle Controls
- **Eager Fetching:** The `@OneToMany` relationship on the `Person` entity utilizes `FetchType.EAGER` fetching. This ensures that when a person is loaded from the database, their associated list of employers is retrieved in a single unified operation, streamlining the nested table layout within the HTML UI.
- **Cascade Deletion:** The relationship is configured with `CascadeType.REMOVE` (also represented as `ON DELETE CASCADE` in the schema). When a `Person` entity is deleted, Hibernate automatically propagates the deletion to remove all associated `Employer` history records, preventing relational orphaned entries and enforcing strict database integrity.
