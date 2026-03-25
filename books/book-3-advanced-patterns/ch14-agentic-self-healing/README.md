# ch14 - Agentic Self-Healing Security

- Book: book-3-advanced-patterns
- Chapter: ch14
- Status: executable-demo
- Suggested stack: ArgoCD/OPA

## Objective
Automated policy rollback with safeguards.

## Mitigation Logic
- Control family: `INCIDENT` (containment workflow and response execution).
- Core robust security decision model in code:
- Enforced security baseline (production copy/paste pattern):
  - Reduces MTTR and containment delay through prepared procedures.
  - Lowers impact duration and operational risk.
  - Increases MTTR and incident blast radius due to weak coordination.
  - Raises risk due to delayed containment.
- Example:
  - `GET /api/demo?incidents=3` -> contained, lower MTTR.

## Demo Scope
- Use scenario input like `incidents` to model response posture.
- Validate impact with `mttrMinutes`, `runbookUsed`, and `riskScore`.

## Run Plan
1. Start service: `mvn spring-boot:run`.
2. Run secure path: `GET /api/demo?incidents=3`.
3. Run baseline path with same inputs and compare MTTR and response posture.

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
