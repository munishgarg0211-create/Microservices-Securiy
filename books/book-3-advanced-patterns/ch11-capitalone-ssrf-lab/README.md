# ch11 - Case Study: Capital One SSRF

- Book: book-3-advanced-patterns
- Chapter: ch11
- Status: executable-demo
- Suggested stack: Flask/AWS mock

## Objective
SSRF chain and IAM hardening demo.

## Mitigation Logic
- Control family: `AUTHZ` (identity and object-level authorization enforcement).
- Core robust security decision model in code:
- Enforced security baseline (production copy/paste pattern):
  - Requires valid ownership/admin conditions for object access.
  - Returns lower risk when unauthorized access is denied.
  - Allows access without ownership validation (BOLA-style exposure).
  - Returns high risk when unauthorized access is effectively allowed.
- Example:
  - `GET /api/demo?actor=alice&owner=bob` -> deny + lower exposure.

## Demo Scope
- Use authz parameters like `actor` and `owner` to simulate ownership and privilege checks.
- Observe `controlDecision`, `expectedBehavior`, and `riskScore` to compare impact.

## Run Plan
1. Start service: `mvn spring-boot:run`.
2. Run secure path: `GET /api/demo?actor=alice&owner=bob` and verify deny behavior.

## Acceptance Criteria
- The reference pattern implements a deterministic, control-enforced decision for the chapter scenario.
- API response includes measurable fields (`controlDecision`, `expectedBehavior`, `riskScore`) and tests validate them.

## Generated Demo Sample
- Runtime: Spring Boot 3.x, Java 21
- API: GET /api/demo (supports `mode` + scenario params)
- Tests: DemoServiceTest, DemoControllerTest

## Code Demonstration Map

## Quick Start
- Run: mvn spring-boot:run
- Test: mvn test
