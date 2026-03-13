# ch6 - Rate Limiting Algorithms Simulator

- Book: book-1-foundations-core-security
- Chapter: ch6
- Status: executable-demo
- Algorithms: Token Bucket, Leaky Bucket

## Objective
Simulate and compare the two most common rate-limiting algorithms used in microservices: Token Bucket (allowing controlled bursts) and Leaky Bucket (smooth constant rate).

## Mitigation Logic
- **Control family**: `PERIMETER_SECURITY` / `PROTECTION`.
- **Token Bucket**:
    - Tokens are added to a "bucket" at a fixed rate.
    - Each request consumes a token.
    - Allows bursts of traffic up to the bucket's capacity.
    - Common in API Gateways like Spring Cloud Gateway.
- **Leaky Bucket**:
    - Requests enter a bucket and "leak" out at a constant rate.
    - Smoothing out traffic regardless of burst size.
    - Useful for protecting downstream services with rigid processing limits.

## Demo Scope
- **Algorithm Comparison**: Toggle between `token` and `leaky` implementations via query parameters.
- **Stateful Simulation**: The simulators track state (tokens/water level) in-memory to demonstrate logic flow.
- **Secure Mode** (`mode=secure`):
    - Applies the selected algorithm and returns `throttle` if limits are exceeded.
- **Insecure Mode** (`mode=insecure`):
    - Demonstrates the risk of unprotected endpoints.

## Run Plan
1. Start service: `mvn spring-boot:run` or `mvn test`.
2. **Token Bucket Burst**:
    - Call 10 times quickly: `GET /api/demo?mode=secure&algo=token`.
    - Call 11th time: Observe `throttle`.
3. **Compare with Leaky Bucket**:
    - Observe how Leaky Bucket behaves more strictly under the same load.

## Code Map
- `TokenBucket.java`: Logic for token replenishment and burst handling.
- `LeakyBucket.java`: Logic for constant-rate "leaking" of requests.
- `DemoService.java`: Orchestrates the simulation.
- `DemoController.java`: API for toggling algorithms and modes.
