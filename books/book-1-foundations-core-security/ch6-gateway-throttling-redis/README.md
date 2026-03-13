# ch6 - Gateway Throttling with Redis

- Book: book-1-foundations-core-security
- Chapter: ch6
- Status: executable-demo
- Suggested stack: Java 21, Spring Cloud Gateway, Redis

## Objective
Demonstrate distributed rate limiting and throttling at the API Gateway layer using Redis to protect backend microservices from traffic spikes and DoS attacks.

## Secure Implementation Logic
- **Control Family:** `RESILIENCE` (Perimeter Defense and Traffic Control).
- **Core Principle:** Global Rate Limiting. In a multi-instance microservice environment, local rate limiting is insufficient. Perimeter defense must be distributed. By using Redis as a centralized state store, the API Gateway can enforce consistent global limits across all instances, ensuring that no single client can overwhelm the system.
- **Implementation:**
    - The project demonstrates the logic used by a Reactive API Gateway.
    - It uses Spring Cloud Gateway's `RedisRateLimiter` (simulated logic) to track request counts against a global budget in Redis.
    - Requests that exceed the configured `replenishRate` or `burstCapacity` are rejected at the edge with a 429 Too Many Requests status.
    - This project provides the secure reference endpoint that would be protected by such a gateway filter.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point representing a protected backend service.
- `src/main/java/.../DemoService.java`: Provides the secure response payload returning from a successfully throttled gateway.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying the endpoint's availability and metadata headers.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the resilient response logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl: `curl "http://localhost:8080/api/demo"`

## Acceptance Criteria
- Requests passing the gateway resulting in `controlDecision: allow`.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
- The implementation uses Reactive patterns (Mono/Flux) consistent with Spring Cloud Gateway architecture.
