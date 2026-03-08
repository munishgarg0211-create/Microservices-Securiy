# ch5 - Rate, Geo-Fencing, IP Controls

- Book: book-1-foundations-core-security
- Chapter: ch5
- Status: scaffolded
- Suggested stack: Redis, GeoIP DB

## Objective
Context-aware edge controls and policy decisions.

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
