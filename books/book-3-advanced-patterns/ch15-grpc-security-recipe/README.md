# ch15 - Recipe: gRPC Security

- Book: book-3-advanced-patterns
- Chapter: ch15
- Status: executable-demo
- Suggested stack: gRPC

## Objective
mTLS and authorization interceptors for gRPC.

## Mitigation Logic
- Control family: `TRANSPORT` (TLS/mTLS and trust-channel validation).
- Core robust security decision model in code:
- Enforced security baseline (production copy/paste pattern):
  - Enforces encrypted transport and identity trust constraints.
  - Produces lower risk when transport requirements are met.
  - Permits weak/legacy transport conditions.
  - Produces higher risk for interception and impersonation exposure.
- Example:
  - `GET /api/demo?tlsVersion=1.0&trustedClient=false` -> deny.

## Demo Scope
- Use transport parameters like `tlsVersion` and `trustedClient` to model trust decisions.
- Validate impact with `controlDecision`, `expectedBehavior`, and `riskScore`.

## Run Plan
1. Start service: `mvn spring-boot:run`.
2. Run secure path: `GET /api/demo?tlsVersion=1.0&trustedClient=false`.
3. Run baseline path with same inputs and compare control decision/risk.

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
