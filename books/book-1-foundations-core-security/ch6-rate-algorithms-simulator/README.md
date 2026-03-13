# ch6 - Rate Limiting Algorithms Simulator

- Book: book-1-foundations-core-security
- Chapter: ch6
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Custom Algorithm Impls

## Objective
Demonstrate and compare two fundamental rate limiting algorithms used in microservice defense: the Token Bucket and the Leaky Bucket.

## Secure Implementation Logic
- **Control Family:** `RESILIENCE` (System Stability and Defense-in-Depth).
- **Core Principle:** Traffic Shaping and Smoothing. Rate limiting is a critical defense against DoS attacks and resource exhaustion. This project provides a simulator to understand how different algorithms manage traffic bursts and steady-state load.
- **Implementation:**
    - **Token Bucket:** Allows for bursts of traffic as long as "tokens" are available in the bucket. Once depleted, traffic is limited to a steady refill rate. Ideal for handling short spikes.
    - **Leaky Bucket:** Enforces a perfectly smooth output rate regardless of the input burstiness. Requests enter a "leaky" bucket and are processed at a constant rate. Excess requests are discarded once the bucket is full.
    - The `DemoService` allows the user to switch between these algorithms to observe their behavior under different simulated loads.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts an `algorithm` parameter to simulate different rate limiting behaviors.
- `src/main/java/.../DemoService.java`: Orchestrates the simulation logic across different bucket implementations.
- `src/main/java/.../TokenBucket.java`: Implementation of the Token Bucket algorithm.
- `src/main/java/.../LeakyBucket.java`: Implementation of the Leaky Bucket algorithm.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying burst handling and throttling logic for both algorithms.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the algorithm simulation logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Token Bucket: `curl "http://localhost:8080/api/demo?algorithm=token"`
    - Leaky Bucket: `curl "http://localhost:8080/api/demo?algorithm=leaky"`

## Acceptance Criteria
- Valid requests within capacity resulting in `controlDecision: allow`.
- Requests exceeding the bucket capacity resulting in `controlDecision: throttle`.
- Clear differentiation in behavior between 'token' (bursty) and 'leaky' (smooth) algorithms during repeated calls.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
