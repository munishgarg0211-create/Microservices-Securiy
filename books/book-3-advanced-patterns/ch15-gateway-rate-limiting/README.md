# ch15 - Recipe: Gateway Rate Limiting

- Book: book-3-advanced-patterns
- Chapter: ch15
- Status: scaffolded
- Suggested stack: Gateway + Redis

## Objective
Per-user token-aware rate limiting.

## Demo Scope
- Build a minimal, runnable demo focused only on this concept.
- Include one insecure path and one secured/fixed path where applicable.
- Add automated checks or tests for the security behavior.

## Run Plan
1. Add service code under src/.
2. Add deployment/runtime config under infra/.
3. Add local run instructions and test commands.

## Acceptance Criteria
- Concept is demonstrable in isolation.
- Security control is measurable (logs, test, or policy decision).
- Failure mode and secure mode are both documented.

## Generated Demo Sample
- Runtime: Spring Boot 3.x, Java 21
- API: GET /api/demo
- Tests: DemoServiceTest, DemoControllerTest

## Quick Start
- Run: mvn spring-boot:run
- Test: mvn test
