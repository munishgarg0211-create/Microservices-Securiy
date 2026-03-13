# ch1 - Secure SDLC Pipeline

- Book: book-1-foundations-core-security
- Chapter: ch1
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5

## Objective
Demonstrate secure SDLC (Software Development Life Cycle) integration by implementing automated pipeline gates that enforce SAST, SCA, and vulnerability thresholds.

## Secure Implementation Logic
- **Control Family:** `POLICY` (Security Policies and Automated Pipeline Gates).
- **Core Principle:** Shifting Security Left. Security must be an automated, non-negotiable part of the build pipeline. Artifacts should only be promoted if they meet a predefined security baseline, ensuring that vulnerable code is caught early and never reaches production.
- **Implementation:**
    - The `DemoService` acts as a CI/CD orchestrator's security gate.
    - It evaluates `sastPassed` (Static Application Security Testing) and `scaPassed` (Software Composition Analysis).
    - It enforces a "Zero Critical Vulnerabilities" policy by checking `criticalVulnsCount`.
    - If any security scan fails or if critical vulnerabilities are detected, the pipeline is `blocked`, preventing the deployment of insecure software.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts scan results and vulnerability counts to simulate pipeline gate outcomes.
- `src/main/java/.../DemoService.java`: Contains the logic for the automated security pipeline gates.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying pipeline pass/block scenarios based on security scan results.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the pipeline gate logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success (Pass): `curl "http://localhost:8080/api/demo?sastPassed=true&scaPassed=true&criticalVulns=0"`
    - Success (Block): `curl "http://localhost:8080/api/demo?sastPassed=true&scaPassed=true&criticalVulns=5"`

## Acceptance Criteria
- Pipelines where all scans pass and no critical vulns exist resulting in `controlDecision: pass`.
- Pipelines with scan failures or high-severity vulnerabilities resulting in `controlDecision: block` and a high risk score.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
