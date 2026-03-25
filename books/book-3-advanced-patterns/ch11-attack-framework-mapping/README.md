# ch11 - Preventive Framework Mapping

- Book: book-3-advanced-patterns
- Chapter: ch11
- Status: executable-demo
- Suggested stack: Templates

## Objective
Map ATT&CK/Kill Chain to controls.

## Mitigation Logic
- Control family: `THREAT` (attack-path mitigation via control coverage).
- Core risk model in code:
  - `residualRisk = exploitability - controlCoverage`
  - `controlDecision = mitigated` when `residualRisk <= 20`, otherwise `exposed`.
- Enforced security baseline (production copy/paste pattern):
  - Uses stronger control coverage to reduce reachable attack paths.
  - Lowers residual risk and trends toward `mitigated`.
  - Uses weaker control coverage leaving threat paths exposed.
  - Increases residual risk and trends toward `exposed`.
- Example:
  - `GET /api/demo?exploitability=80&controlCoverage=85` -> mitigated.

## Demo Scope
- Use threat parameters like `exploitability` and `controlCoverage`.
- Validate mitigation effect through `residualRisk`, `controlDecision`, and `riskScore`.

## Run Plan
1. Start service: `mvn spring-boot:run`.
2. Run secure path: `GET /api/demo?exploitability=80&controlCoverage=85`.

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
