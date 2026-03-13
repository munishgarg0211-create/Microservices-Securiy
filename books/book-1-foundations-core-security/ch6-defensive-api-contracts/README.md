# ch6 - Defensive API Contracts

- Book: book-1-foundations-core-security
- Chapter: ch6
- Status: executable-demo
- Stack: Spring Boot 3.3.5, Java 21

## Objective
Implement defensive programming patterns like **Deadline Propagation** and **Fail-Fast** logic to protect system resources.

## Mitigation Logic
- **Control family**: `AVAILABILITY` / `RESOURCE_PROTECTION`.
- **Deadline Propagation**: Passing a "timeout budget" (via `X-Deadline-Ms` header) across service boundaries.
- **Fail-Fast**: Checking the deadline budget *before* starting expensive work. If the budget is already exhausted, the request is rejected immediately.
- **Abandonment**: Checking the deadline *during* work. if the budget expires mid-process, the operation is abandoned to prevent further resource waste.

## Demo Scope
The demo uses a `DeadlineInterceptor` to manage a `ThreadLocal` deadline context.
- **Secure Mode** (`mode=secure`):
    - Honors the `X-Deadline-Ms` header.
    - Fails fast if the deadline has already passed.
    - Rejects results if the deadline expires during a `Thread.sleep` simulation.
- **Insecure Mode** (`mode=insecure`):
    - Ignores the deadline context and always attempts to complete the work.

## Run Plan
1. Start service: `mvn spring-boot:run` or `mvn test`.
2. **Success Case**: `GET /api/demo?mode=secure&workDurationMs=100` with header `X-Deadline-Ms: 500`.
3. **Fail-Fast Case**: `GET /api/demo?mode=secure&workDurationMs=100` with header `X-Deadline-Ms: -10`.
4. **Abandonment Case**: `GET /api/demo?mode=secure&workDurationMs=200` with header `X-Deadline-Ms: 100`.

## Code Map
- `DeadlineContext.java`: ThreadLocal storage for the request deadline.
- `DeadlineInterceptor.java`: Extracts the deadline from the HTTP header.
- `WebConfig.java`: Registers the interceptor.
- `DemoService.java`: Implements the fail-fast and abandonment checks.
- `DemoControllerTest.java`: Verifies the defensive contract outcomes.
