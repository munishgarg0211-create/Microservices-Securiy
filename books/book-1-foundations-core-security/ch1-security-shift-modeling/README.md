# ch1 - Monolith to Microservices Security Shift

- Book: book-1-foundations-core-security
- Chapter: ch1
- Status: scaffolded
- Suggested stack: Spring Boot, Mermaid, STRIDE

## Objective
Threat model comparing monolith vs distributed architecture.

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
