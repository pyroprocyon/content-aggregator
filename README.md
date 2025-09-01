# Content Aggregator API

This project is a robust, asynchronous Content Aggregator API built with modern Spring Boot. It serves as a practical demonstration of core Java concurrency concepts, professional testing practices, and scalable backend design patterns. The application fetches articles from multiple external news sources in parallel, caches them in a local database, and exposes them through a clean, RESTful API.

## Core Concepts & Features Demonstrated

This project was built as a deep-dive learning exercise to master the following principles:

* **Asynchronous Programming:** Utilizes Java's `CompletableFuture` and a custom `Executor` to fetch data from multiple external APIs concurrently, ensuring a non-blocking and highly efficient aggregation process.

* **Scalable Caching Strategy:** Implements a robust caching mechanism where a background job, managed by Spring's `@Scheduled` annotation, periodically fetches and persists articles. This decouples the user-facing API from the latency and unreliability of external services, ensuring consistently fast response times.

* **Clean Architectural Design:** Follows a classic layered architecture (Controller, Service, Repository) with a strong emphasis on the Open/Closed Principle through the use of a `ContentSource` interface for easy extension.

* **Professional Testing Suite:** Features a comprehensive set of tests, demonstrating a clear understanding of the testing pyramid:
    * **Unit Tests:** Services are tested in complete isolation using JUnit 5 and Mockito to verify business logic.
    * **Integration Tests:** The full application stack is tested using `@SpringBootTest` and `MockMvc` to ensure all components work together correctly, with background jobs disabled using Spring Profiles for a predictable test environment.

* **External API Consumption:** Demonstrates best practices for consuming external REST APIs using Spring's `RestTemplate` and mapping external DTOs to an internal domain model to create a resilient Anti-Corruption Layer.

---

## Architectural Overview

The application is designed with two distinct workflows to ensure performance and reliability.

1.  **Background Caching Workflow (Asynchronous):**
    * A Spring-managed scheduler (`@Scheduled`) triggers the `ContentService` at a fixed interval.
    * The service dispatches parallel requests to multiple `ContentSource` implementations using a dedicated thread pool.
    * `CompletableFuture.allOf()` is used to wait for all asynchronous fetches to complete.
    * The aggregated, unique articles are saved to the H2 in-memory database, which acts as a cache.

2.  **User Request Workflow (Synchronous & Fast):**
    * A user sends a `GET` request to the `ContentController`.
    * The controller calls the `ArticleRepository` directly.
    * Articles are served instantly from the fast, local database cache.

---

## API Endpoints

The primary endpoint for retrieving content is:

| Method | Path            | Description                              |
| :----- | :-------------- | :--------------------------------------- |
| `GET`  | `/api/articles` | Retrieves a list of all cached articles. |

*(Future enhancements will include pagination and filtering on this endpoint).*

---

## Technologies Used

* **Language:** Java 17+
* **Framework:** Spring Boot 3.x
    * Spring Web
    * Spring Data JPA
    * Spring Scheduling
* **Database:** H2 In-Memory Database
* **Concurrency:** Java `ExecutorService` & `CompletableFuture`
* **Testing:** JUnit 5, Mockito, Hamcrest, MockMvc
* **Build Tool:** Maven
