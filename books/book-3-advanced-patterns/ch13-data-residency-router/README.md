# ch13 - Data Residency and Privacy

- Book: book-3-advanced-patterns
- Chapter: ch13
- Status: executable-demo
- Suggested stack: Spring, DB

## Objective
Geo-sharding router with privacy controls.

## Mitigation Logic
- Control family: `COMPLIANCE` (policy evidence and governance constraint enforcement).
- Core robust security decision model in code:
- Enforced security baseline (production copy/paste pattern):
  - Preserves auditable control outcomes and data-governance boundaries.
  - Lowers risk when compliance checks pass.
  - Allows non-compliant flow progression without evidence.
  - Raises legal/regulatory risk.
- Example:
  - `GET /api/demo?dataRegion=eu-west-1&requestRegion=us-east-1&evidenceAttached=false` -> violation.

## Demo Scope
- Use compliance parameters like `dataRegion`, `requestRegion`, and `evidenceAttached`.
- Compare `controlDecision`, `expectedBehavior`, and `riskScore`.

## Run Plan
1. Start service: `mvn spring-boot:run`.
2. Run secure path: `GET /api/demo?dataRegion=eu-west-1&requestRegion=us-east-1&evidenceAttached=false`.
3. Run baseline path with same inputs and compare compliance decision/risk.

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
