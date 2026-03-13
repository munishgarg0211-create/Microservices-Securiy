# ch6 - Circuit Breaker and Bulkhead Security

- Book: book-1-foundations-core-security
- Chapter: ch6
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Resilience4j

## Objective
Demonstrate service resilience by implementing the Circuit Breaker and Bulkhead patterns to protect microservices from cascading failures and resource exhaustion.

## Secure Implementation Logic
- **Control Family:** `RESILIENCE` (System Stability and Fault Tolerance).
- **Core Principle:** Fail Fast and Isolate. In a microservices architecture, downstream failures are inevitable. A secure system must detect these failures instantly (Circuit Breaker) and isolate them to prevent thread exhaustion (Bulkhead), eventually failing over to a safe fallback.
- **Implementation:**
    - The `DemoService` wraps unstable downstream calls using Resilience4j annotations (`@CircuitBreaker`, `@Bulkhead`).
    - The **Circuit Breaker** monitors failure rates and transitions to an `OPEN` state to block further calls when thresholds are exceeded.
    - The **Bulkhead** limits the number of concurrent calls to the specific downstream service, ensuring that slow responses don't consume all available system threads.
    - A `fallback` method provides a consistent, safe response (e.g., static or cached data) to maintain system availability.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that triggers protected downstream calls.
- `src/main/java/.../DemoService.java`: Implements resilience patterns using Circuit Breaker and Bulkhead annotations.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying successful calls and fallback activation.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the resilience and fallback logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success (Healthy): `curl "http://localhost:8080/api/demo?shouldFail=false"`
    - Success (Fallback): `curl "http://localhost:8080/api/demo?shouldFail=true"`

## Acceptance Criteria
- Healthy downstream calls resulting in `controlDecision: allow`.
- Downstream failures or latency triggering `controlDecision: fallback`.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
