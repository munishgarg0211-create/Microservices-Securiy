# ch13 - Audit Trails Best Practices

- Book: book-3-advanced-patterns
- Chapter: ch13
- Status: executable-demo
- Suggested stack: Kafka/WORM

## Objective
Tamper-evident audit log implementation.

## Mitigation Logic
- Control family: `OBSERVABILITY` (security signal enrichment and triage coverage).
- Core robust security decision model in code:
- Enforced security baseline (production copy/paste pattern):
  - Improves detection quality and response readiness.
  - Lowers operational risk through better visibility.
  - Under-instrumented telemetry weakens threat detection.
  - Raises risk due to reduced signal fidelity and triage coverage.
- Example:
  - `GET /api/demo?events=50&suspicious=7` -> high enrichment/triage.

## Demo Scope
- Use telemetry parameters like `events` and `suspicious`.
- Compare `enrichedEvents`, `triagedEvents`, and `riskScore` to show impact.

## Run Plan
1. Start service: `mvn spring-boot:run`.
2. Run secure path: `GET /api/demo?events=50&suspicious=7`.
3. Run baseline path with same inputs and compare telemetry enrichment/triage.

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
