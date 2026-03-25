# ch15 - Recipe: Kafka Security

- Book: book-3-advanced-patterns
- Chapter: ch15
- Status: executable-demo
- Suggested stack: Kafka

## Objective
SASL/SCRAM + SSL recipe project.

## Mitigation Logic
- Control family: `MESSAGING` (message integrity, producer trust, and channel protection).
- Core robust security decision model in code:
- Enforced security baseline (production copy/paste pattern):
  - Rejects untrusted or malformed events before processing.
  - Lowers risk of poisoned or spoofed message flows.
  - Accepts unauthenticated/invalid events.
  - Raises risk for event-bus compromise and downstream corruption.
- Example:
  - `GET /api/demo?producerAuth=false&schemaValid=false&encryptedChannel=false` -> reject.

## Demo Scope
- Use message parameters like `producerAuth`, `schemaValid`, and `encryptedChannel`.
- Validate message-path control through `controlDecision` and `riskScore`.

## Run Plan
1. Start service: `mvn spring-boot:run`.
2. Run secure path: `GET /api/demo?producerAuth=false&schemaValid=false&encryptedChannel=false`.
3. Run baseline path with same inputs and compare message acceptance behavior.

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
