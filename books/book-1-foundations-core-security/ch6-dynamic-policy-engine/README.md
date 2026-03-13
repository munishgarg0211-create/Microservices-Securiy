# ch6 - Dynamic Policy Engine

- Book: book-1-foundations-core-security
- Chapter: ch6
- Status: executable-demo
- Stack: Spring Boot 3.3.5, Java 21, Jackson

## Objective
Implement a runtime-pluggable security policy engine that evaluates rules from an externalized JSON source without requiring code changes.

## Mitigation Logic
- **Control family**: `GOVERNANCE` / `ADMISSION_CONTROL`.
- **Externalized Logic**: Security rules (e.g., "all images must be signed", "no critical vulnerabilities allowed") are stored in `policy.json`.
- **Runtime Evaluation**: The `PolicyEngine` loads these rules at startup and evaluates them against request/deployment parameters.
- **Fail-Closed**: If the policy engine cannot be reached or rules fail, the default behavior should be to block (though this demo focus is on the evaluation logic).

## Demo Scope
- **Policy Definition**: A `policy.json` file in `src/main/resources` defines the active rules and their risk impact.
- **Secure Mode** (`mode=secure`):
    - Passes parameters to the `PolicyEngine`.
    - Returns a `block` decision if any rule is violated (risk > 0).
    - Lists specific violations (e.g., `require-signed-image`).
- **Insecure Mode** (`mode=insecure`):
    - Bypasses the engine entirely, representing a "blind" deployment.

## Run Plan
1. Start service: `mvn spring-boot:run` or `mvn test`.
2. **Policy Pass**: `GET /api/demo?mode=secure&imageSigned=true&hasSbom=true&criticalVulns=0`.
3. **Policy Block**: `GET /api/demo?mode=secure&imageSigned=false`.
4. **Bypass**: `GET /api/demo?mode=insecure&imageSigned=false`.

## Code Map
- `policy.json`: The source of truth for security rules.
- `PolicyEngine.java`: Logic for rule parsing and parameter matching.
- `DemoService.java`: Orchestrates the policy check.
- `DemoControllerTest.java`: Validates enforcement across different parameter combinations.
