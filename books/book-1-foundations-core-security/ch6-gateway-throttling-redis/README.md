# ch6 - Gateway Throttling with Redis

- Book: book-1-foundations-core-security
- Chapter: ch6
- Status: executable-demo
- Stack: Spring Cloud Gateway, Spring Data Redis (Reactive), Netty

## Objective
Implement distributed rate limiting at the API Gateway level using Redis to protect the microservices perimeter from traffic spikes and DoS attacks.

## Mitigation Logic
- **Control family**: `PERIMETER_SECURITY` / `THROTTLING`.
- **Distributed State**: Unlike local rate limiting, this implementation uses Redis to track request counts across multiple gateway instances.
- **Spring Cloud Gateway Integration**: Uses the `RequestRateLimiter` filter factory with a `RedisRateLimiter`.
- **Key Resolution**: Requests are throttled based on a `KeyResolver` (e.g., user ID, IP, or API Key).

## Demo Scope
- **Reactive Stack**: Built on WebFlux/Netty for high-performance non-blocking I/O.
- **Redis Rate Limiter Configuration**:
    - `replenishRate`: How many requests per second are allowed.
    - `burstCapacity`: The maximum number of requests allowed in a single second.
- **Secure Mode** (`mode=secure`):
    - Routes through the gateway filters where Redis-backed rate limiting is active.
- **Insecure Mode** (`mode=insecure`):
    - Demonstrates a path that skips perimeter hardening.

## Run Plan
1. **Prerequisite**: A running Redis instance on `localhost:6379`.
2. Start service: `mvn spring-boot:run`.
3. **Throttling Demo**:
    - Perform rapid requests: `GET /api/demo?mode=secure&user=alice`.
    - Observe `HTTP 429 Too Many Requests` after exceeding burst capacity.
4. **Compare**: `GET /api/demo?mode=insecure` which bypasses these controls (for architectural comparison).

## Code Map
- `RateLimiterConfig.java`: Defines the `KeyResolver` logic.
- `application.yml`: Configures the Gateway routes and `RedisRateLimiter` parameters.
- `DemoController.java`: A simple reactive endpoint being protected.
- `DemoControllerTest.java`: Verifies the reactive integration and mock Redis setup.
