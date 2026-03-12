# ch4 - Spring Auth Custom Autoconfiguration Starter Demo

- Book: book-1-foundations-core-security
- Chapter: ch4
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Spring Security (OAuth2 Resource Server)

## Objective
Demonstrate how platform security teams can enforce consistent authentication policies across hundreds of microservices using centralized Spring Boot Starters (Autoconfiguration).

## Mitigation Logic
- Control family: `AUTHZ` (Authorization and Access Control) / Policy Distribution
- **Good practice:** Extract repetitive security configuration (like validating JWTs against a specific corporate Identity Provider) into a shared, version-controlled library (`spring-auth-starter`). This ensures "Secure by Default" behavior across the fleet.
- **Bad practice:** Allowing every individual microservice team to write their own `SecurityFilterChain`. This leads to configuration drift, missed updates, and inconsistent enforcement of corporate security baselines.

## Key Concepts Demonstrated
1. **`@ConfigurationProperties` (`GlobalSecurityProperties`)**: Defines typed configuration properties (`corp.security.global.*`) that developers can override in their `application.yml` (if permitted).
2. **Conditional Autoconfiguration (`@ConditionalOnProperty`)**: The shared `SecurityConfig` is activated by default but can be toggled via properties.
3. **Zero-Boilerplate Security**: Notice that `DemoController` requires no local security configuration. Because the starter is on the classpath, the endpoint is automatically protected by the centralized OAuth2 Resource Server constraints.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoApplication.java`: Spring Boot entrypoint.
- `src/main/java/.../GlobalSecurityProperties.java`: The configuration property bindings.
- `src/main/java/.../SecurityConfig.java`: The shared SecurityFilterChain that validates JWTs against the issuer defined in the properties.
- `src/main/java/.../DemoController.java`: A basic business endpoint relying entirely on the autoconfigured security.
- `src/test/java/.../DemoControllerTest.java`: Validates that the application context successfully loads the properties and enforces the JWT requirement on the endpoints.
<!-- CODE_MAP_END -->

## Quick Start
You can build and test this project via Maven. 
- `mvn clean test`
