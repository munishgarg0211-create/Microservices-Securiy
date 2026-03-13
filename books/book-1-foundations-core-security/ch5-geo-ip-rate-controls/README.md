# ch5 - Geo-IP and Rate Controls

- Book: book-1-foundations-core-security
- Chapter: ch5
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Resilience4j, Spring Security Filter Chain

## Objective
Demonstrate how to mitigate denial-of-service (DDoS) and brute-force attacks at the application edge using sophisticated Rate Limiting and Geographic IP filtering before traffic reaches backend business logic.

## Security Controls Demonstrated
- Control family: `THREAT` (Threat Mitigation / Availability)
- **Rate Limiting (Resilience4j):**
  - *Anti-Pattern:* An API endpoint linearly processes every incoming request. During a sudden spike or targeted DDoS attack, thread pools exhaust, database connections saturate, and the entire microservice crashes.
  - *Secure Fix:* The target endpoint is decorated with `@RateLimiter(name = "api-limiter")`. `application.yml` restricts traffic (e.g., 5 requests per 10 seconds). Excess traffic is immediately dropped with a clean `429 Too Many Requests` response via `@RestControllerAdvice` without wasting application threads.
- **Geo-IP Filtering (SecurityFilterChain):**
  - *Anti-Pattern:* A regional application serving local customers unconditionally accepts traffic from global threat actors or known botnet regions, exposing its attack surface needlessly.
  - *Secure Fix:* A custom `OncePerRequestFilter` (`GeoIpFilter.java`) is injected *early* into the Spring Security chain. It intercepts the origin IP (or a routed header), consults a localized blocklist, and immediately drops malicious traffic with `403 Forbidden` before expensive authentication or business logic is evaluated.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/resources/application.yml`: Defines the Resilience4j Rate Limiting parameters (e.g., `limitForPeriod: 5`).
- `src/main/java/.../GeoIpFilter.java`: Intercepts traffic early, simulating dropping traffic from blocklisted countries.
- `src/main/java/.../SecurityConfig.java`: Registers the `GeoIpFilter` immediately before standard security filters.
- `src/main/java/.../DemoController.java`: A target API explicitly protected by `@RateLimiter`.
- `src/main/java/.../GlobalExceptionHandler.java`: Securely translates `RequestNotPermitted` into HTTP 429.
- `src/test/java/.../RateLimitingAndGeoIpTest.java`: Integration tests verifying a single Geo-IP drop, and sequentially asserting that the 6th rapid request inside the rate limit window is properly rejected.
<!-- CODE_MAP_END -->

## Quick Start
You can build and test this project via Maven. 
- `mvn clean test`
