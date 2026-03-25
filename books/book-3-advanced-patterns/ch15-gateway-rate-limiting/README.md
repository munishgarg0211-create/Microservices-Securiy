# ch15 - Recipe: Gateway Rate Limiting

- Book: book-3-advanced-patterns
- Chapter: ch15
- Status: executable-demo
- Suggested stack: Gateway + Redis

## Objective
Per-user token-aware rate limiting.

## Mitigation Logic
- Control family: `RATE_LIMIT` (abuse throttling and edge protection).
- Core robust security decision model in code:
- Enforced security baseline (production copy/paste pattern):
  - Reduces brute-force and abuse blast radius via bounded throughput.
  - Reports blocked-over-limit behavior and lower risk.
  - Leaves endpoints exposed to unbounded request spikes.
  - Reports elevated risk when abuse traffic is not constrained.
- Example:
  - `GET /api/demo?requests=120&limit=100` -> throttled overflow.

## Demo Scope
- Use traffic parameters like `requests` and `limit` to simulate abuse throttling.
- Compare `allowedRequests`, `blockedRequests`, and `riskScore` by mode.

## Run Plan
1. Start service: `mvn spring-boot:run`.
2. Run secure path: `GET /api/demo?requests=120&limit=100` and verify throttling.
3. Run baseline path with same inputs and compare blocked vs allowed counts.

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
