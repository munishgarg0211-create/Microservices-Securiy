# ch8 - Mesh Observability

- Book: book-2-infrastructure-devsecops
- Chapter: ch8
- Status: scaffolded
- Suggested stack: Kiali, Jaeger

## Objective
Trace and verify policy enforcement events.

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
