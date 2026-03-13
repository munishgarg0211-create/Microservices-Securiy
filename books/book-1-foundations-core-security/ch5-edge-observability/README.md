# ch5 - Edge Observability

- Book: book-1-foundations-core-security
- Chapter: ch5
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Spring Boot Actuator, Micrometer (Prometheus), Spring Security

## Objective
Demonstrate how to safely expose vital operational metrics (Observability) while preventing information disclosure to attackers. Additionally, demonstrate how to establish Distributed Tracing using Correlation IDs (MDC) at the edge.

## Security Controls Demonstrated
- Control family: `OBSERVABILITY` (Logging, Metrics, Tracing)
- **Secure Actuator Endpoints:**
  - *Anti-Pattern:* A developer adds `spring-boot-starter-actuator` and exposes `*` to the web without authentication. Attackers hit `/actuator/env` to steal database passwords or `/actuator/heapdump` to steal session tokens from memory.
  - *Secure Fix:* `SecurityConfig.java` strictly protects `/actuator/**` requiring HTTP Basic Authentication and a specific scraper role (`ACTUATOR_ADMIN`). Only safe endpoints (`health`, `info`, `prometheus`) are exposed in `application.yml`.
- **Correlation IDs (Distributed Tracing):**
  - *Anti-Pattern:* An attack creates an alert in the API Gateway, but there's no way to find the corresponding logs in the backend Auth Service and Database Service because the logs are isolated and anonymous.
  - *Secure Fix:* An `AuditFilter` acts as the edge boundary. It extracts or generates a unique `X-Trace-Id` and injects it into the SLF4J Mapped Diagnostic Context (MDC). The `application.yml` logging pattern is configured to automatically append this `[%X{traceId}]` to *every single log statement* across the entire application thread, allowing security analysts to trace an attack end-to-end.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/resources/application.yml`: Configures Actuator exposure and explicitly defines the MDC Logback pattern.
- `src/main/java/.../AuditFilter.java`: Extracts/Generates the Trace ID, manages the MDC thread context securely (CRITICAL: wiping it in `finally`), and logs request/response lifecycles.
- `src/main/java/.../SecurityConfig.java`: Implements the distinct security boundary between public `/api` routes and internal `ACTUATOR_ADMIN` routes.
- `src/main/java/.../DemoController.java`: A simple controller proving that standard business logs automatically inherit the MDC context.
- `src/test/java/.../ObservabilitySecurityTest.java`: integration tests proving the Trace ID is appended to public API responses, and proving that the `/actuator/prometheus` endpoint is effectively locked down.
<!-- CODE_MAP_END -->

## Quick Start
You can build and test this project via Maven. 
- `mvn clean test`
