# Content Aggregator API

This project is a robust, asynchronous Content Aggregator API built with modern Spring Boot. It serves as a practical demonstration of core Java concurrency, professional testing practices, and scalable backend design patterns. The application fetches articles from multiple external news sources in parallel, caches them in a local database, and exposes them through a clean, RESTful API with support for pagination and filtering.

## Core Concepts & Features Demonstrated

This project was built as a deep-dive learning exercise to master the following principles:

* **Asynchronous Programming:** Utilizes Java's `CompletableFuture` and a custom `Executor` to fetch data from multiple external news categories concurrently, ensuring a non-blocking and highly efficient aggregation process.

* **Scalable Caching Strategy:** Implements a robust caching mechanism where a background job, managed by Spring's `@Scheduled` annotation, periodically fetches and persists articles. This decouples the user-facing API from the latency and unreliability of external services, ensuring consistently fast response times.

* **Professional Testing Suite:** Features a comprehensive set of tests, demonstrating a clear understanding of the testing pyramid:
    * **Unit Tests:** Services are tested in complete isolation using JUnit 5 and Mockito to verify business logic, including complex asynchronous workflows.
    * **Integration Tests:** The full application stack is tested using `@SpringBootTest` and `MockMvc` to ensure all components work together correctly, with background jobs disabled using Spring Profiles for a predictable test environment.

* **API Pagination and Filtering:** The main API endpoint supports full pagination (`page`, `size`), dynamic sorting (`sortBy`, `sortDir`), and filtering by category (`category`), implemented efficiently at the database level using Spring Data JPA's `Pageable` and derived query methods.

* **Clean Architectural Design:** Follows a classic layered architecture (Controller, Service, Repository). The design was refactored from a complex abstraction to a simpler, enum-driven approach for fetching categories, demonstrating a pragmatic approach to the YAGNI principle.

---

## Architectural Overview

The application is designed with two distinct workflows to ensure performance and reliability.

1.  **Background Caching Workflow (Asynchronous):**
    * A Spring-managed scheduler (`@Scheduled`) triggers the `ContentService` at a fixed interval.
    * The service dispatches parallel requests for each news category to the `NewsApiClient` using a dedicated thread pool.
    * `CompletableFuture.allOf()` is used to wait for all asynchronous fetches to complete.
    * The aggregated, unique articles are saved to the H2 in-memory database, which acts as a cache.

2.  **User Request Workflow (Synchronous & Fast):**
    * A user sends a `GET` request to the `ContentController` with pagination and filtering parameters.
    * The controller calls the `ArticleRepository` via the service layer.
    * A paginated and filtered list of articles is served instantly from the fast, local database cache.

---

## API Endpoints

The primary endpoint for retrieving content is:

| Method | Path            | Description                                                  |
| :----- | :-------------- | :----------------------------------------------------------- |
| `GET`  | `/api/articles` | Retrieves a paginated and filterable list of cached articles. |

**Query Parameters:**

| Parameter  | Type           | Description                                     | Default      |
| :--------- | :------------- |:------------------------------------------------| :----------- |
| `page`     | `int`          | The page number to retrieve.                    | `0`          |
| `size`     | `int`          | The number of articles per page (max 100).      | `20`         |
| `sortBy`   | `String`       | The property to sort by (e.g., `publishedAt`).  | `id`         |
| `sortDir`  | `String`       | The sort direction (`asc` or `desc`).           | `asc`        |
| `category` | `String`       | The category to filter by (e.g., `technology`). | (none)       |

**Example Request:**
`GET http://localhost:8080/api/articles?category=technology&page=0&size=10&sortBy=publishedAt&sortDir=desc`

---

## Technologies Used

* **Language:** Java 17+
* **Framework:** Spring Boot 3.x
    * Spring Web
    * Spring Data JPA
    * Spring Scheduling
* **Database:** H2 In-Memory Database
* **Concurrency:** Java `Executor` & `CompletableFuture`
* **Testing:** JUnit 5, Mockito, Hamcrest, MockMvc
* **Build Tool:** Maven