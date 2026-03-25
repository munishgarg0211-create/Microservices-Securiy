# ch12 - Privilege Segregation

- Book: book-3-advanced-patterns
- Chapter: ch12
- Status: executable-demo
- Suggested stack: Spring + IAM

## Objective
Split-plane privilege boundaries across layers.

## Mitigation Logic
- Control family: `DEFAULT` (secure-by-default control gate).
- Core robust security decision model in code:
- Enforced security baseline (production copy/paste pattern):
  - Applies safety checks before business execution.
  - Returns lower risk profile.
  - Skips checks to illustrate failure mode.
  - Returns higher risk profile.
- Example:
  - `GET /api/demo` -> enforced controls.

## Demo Scope
- Use request parameters to simulate chapter-specific control checks.
- Validate behavior with `controlDecision`, `expectedBehavior`, and `riskScore`.

## Run Plan
1. Start service: `mvn spring-boot:run`.
2. Run secure path: `GET /api/demo`.

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
