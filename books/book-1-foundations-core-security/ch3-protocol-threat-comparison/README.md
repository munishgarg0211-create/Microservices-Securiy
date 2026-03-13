# ch3 - Protocol Threat Comparison

- Book: book-1-foundations-core-security
- Chapter: ch3
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5

## Objective
Demonstrate the automated security evaluation of different communication protocols (gRPC, Kafka, HTTPS, Plain HTTP) against architectural security standards.

## Secure Implementation Logic
- **Control Family:** `THREAT` (Threat Evaluation and Protocol Hardening).
- **Core Principle:** Architectural resilience starts with selecting protocols that provide intrinsic security features like encryption, identity, and integrity by default.
- **Implementation:**
    - The `DemoService` acts as a security evaluator for transport methods.
    - It scores protocols based on their support for TLS 1.3, machine identity (mTLS/SASL), and call-level authorization.
    - Highly secure protocols (like gRPC + TLS) receive low risk scores and are `approved-transport`, while plaintext protocols (like Plain HTTP) are rejected with high risk scores.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts a `protocol` parameter to evaluate its security standing.
- `src/main/java/.../DemoService.java`: Contains the evaluation logic and security scoring for various protocols.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying that secure protocols are approved and insecure ones are rejected.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the protocol scoring engine.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success (Approved): `curl "http://localhost:8080/api/demo?protocol=gRPC%20+%20TLS"`
    - Success (Rejected): `curl "http://localhost:8080/api/demo?protocol=Plain%20HTTP"`

## Acceptance Criteria
- Modern, encrypted protocols resulted in `controlDecision: approved-transport`.
- Plaintext or legacy protocols resulted in `controlDecision: rejected-transport`.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
