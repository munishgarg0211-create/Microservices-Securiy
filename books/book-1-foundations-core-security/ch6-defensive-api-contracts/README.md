# ch6 - Defensive API Contracts

- Book: book-1-foundations-core-security
- Chapter: ch6
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Custom Interceptors

## Objective
Demonstrate defensive programming in microservices by implementing deadline propagation to prevent resource exhaustion and cascading delays.

## Secure Implementation Logic
- **Control Family:** `RESILIENCE` (System Stability and Defense-in-Depth).
- **Core Principle:** Resource Conservation and Fail-Fast. In a distributed system, a slow service can cause a chain reaction of failures. Defensive API contracts enforce a strict "time budget" (deadline). If a service cannot complete its work within the budget, it must fail fast rather than continue consuming resources that won't yield a useful result.
- **Implementation:**
    - The `DemoService` uses a `DeadlineContext` (backed by `ThreadLocal`) to track the remaining time budget for a request.
    - Before starting any heavy processing, the service checks `isExpired()`. If the deadline has already passed, it immediately returns an error.
    - Long-running operations check the deadline periodically. If the deadline expires mid-processing, the operation is abandoned.
    - This ensures that threads and CPU cycles are only spent on requests that have a chance of success, protecting the system from "zombie" requests.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts a `duration` parameter to simulate workload.
- `src/main/java/.../DemoService.java`: Implements defensive logic by checking the `DeadlineContext` at multiple stages.
- `src/main/java/.../DeadlineContext.java`: A utility class for propagating and checking request deadlines across threads.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying fail-fast behavior and successful completion within budgets.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the deadline enforcement logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success (Within Deadline): `curl -H "X-Deadline-Ms: 1000" "http://localhost:8080/api/demo?duration=100"`
    - Success (Fail-Fast): `curl -H "X-Deadline-Ms: 50" "http://localhost:8080/api/demo?duration=200"`

## Acceptance Criteria
- Processing completion when the deadline budget is sufficient resulting in `controlDecision: allow`.
- Immediate rejection or mid-process abandonment when the deadline expires resulting in `controlDecision: block`.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
