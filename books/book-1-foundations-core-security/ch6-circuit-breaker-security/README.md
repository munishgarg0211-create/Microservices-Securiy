# ch6 - Circuit Breaking and Bulkhead for Resilience

- Book: book-1-foundations-core-security
- Chapter: ch6
- Status: executable-demo
- Stack: Resilience4j, Spring Boot 3.3.5, Java 21

## Objective
Demonstrate secure fallback modes and fault isolation using Resilience patterns (Circuit Breaker & Bulkhead).

## Mitigation Logic
- **Control family**: `AVAILABILITY` / `RESILIENCE`.
- **Circuit Breaker**: Prevents a cascading failure by "tripping" when a downstream service is unstable, allowing the system to fail fast and recover.
- **Bulkhead**: Isolates resources for a specific downstream service so that its failure doesn't consume all system threads (preventing resource exhaustion).
- **Graceful Fallback**: Ensures a secure, deterministic "cached" or "static" response is returned instead of a raw error or a hang.

## Demo Scope
The demo simulates an unstable `DownstreamService` that can be instructed to fail or delay.
- **Secure Mode** (`mode=secure`): Uses Resilience4j annotations to wrap the call.
    - If `shouldFail=true`, the Circuit Breaker eventually opens, and the `fallback` method returns "Cached/Static Data".
    - Risk is minimized as the system stays responsive.
- **Insecure Mode** (`mode=insecure`): Calls the service directly.
    - If `shouldFail=true`, the caller is directly impacted by a `RuntimeException`.
    - Risk is elevated due to lack of fault isolation.

## Run Plan
1. Start service: `mvn spring-boot:run` (from project root) or `mvn test` to verify.
2. **Success Case**: `GET /api/demo?mode=secure&shouldFail=false`
3. **Failure Case (Insecure)**: `GET /api/demo?mode=insecure&shouldFail=true` -> Raw error impact.
4. **Failure Case (Secure)**: `GET /api/demo?mode=secure&shouldFail=true` -> Graceful fallback triggered.

## Acceptance Criteria
- Secure mode returns a deterministic fallback decision when downstream fails.
- Insecure mode exposes the caller to raw downstream exceptions.
- Resilience4j configuration (thresholds, window size) is externalized in `application.yml`.

## Code Map
- `DownstreamService.java`: Simulates the unstable dependency.
- `DemoService.java`: Implements `@CircuitBreaker` and `@Bulkhead`.
- `application.yml`: Resilience4j parameters.
- `DemoControllerTest.java`: Verifies the state transitions and fallback outcomes.
