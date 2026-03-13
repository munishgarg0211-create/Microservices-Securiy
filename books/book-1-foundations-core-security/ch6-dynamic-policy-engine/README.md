# ch6 - Dynamic Policy Engine

- Book: book-1-foundations-core-security
- Chapter: ch6
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JSON-based Policies

## Objective
Demonstrate the implementation of a dynamic policy engine that externalizes security rules, allowing for runtime-adjustable security gates without requiring code changes.

## Secure Implementation Logic
- **Control Family:** `POLICY` (Security Governance and Policy-as-Code).
- **Core Principle:** Externalization of Logic. Hardcoding security rules (e.g., "required signed image") into the application makes the system rigid. A secure, flexible architecture externalizes these rules into a policy engine. This allows security teams to update policies (e.g., increasing strictness during an active threat) instantly and globally.
- **Implementation:**
    - The `DemoService` integrates with a `PolicyEngine` to evaluate incoming requests or artifacts.
    - Policy rules (e.g., `require-signed-image`, `max-critical-vulns`) are loaded from an external source (simulated).
    - The engine evaluates the provided metadata against these active rules.
    - Deployment or processing is only allowed if all current policy gates are passed.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts artifact metadata for policy evaluation.
- `src/main/java/.../DemoService.java`: Orchestrates the policy evaluation process.
- `src/main/java/.../PolicyEngine.java`: Implements the core logic for loading and evaluating dynamic JSON rules.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying policy enforcement and violation handling.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the policy evaluation orchestration.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success (Compliant): `curl "http://localhost:8080/api/demo?imageSigned=true&hasSbom=true&criticalVulns=0"`
    - Success (Violation): `curl "http://localhost:8080/api/demo?imageSigned=false&hasSbom=true"`

## Acceptance Criteria
- Artifacts meeting all currently active policy rules resulting in `controlDecision: allow`.
- Metadata violating any active rule resulting in `controlDecision: block` and a list of violations.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
