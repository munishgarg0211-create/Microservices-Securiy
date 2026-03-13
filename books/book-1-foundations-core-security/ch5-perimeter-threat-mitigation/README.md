# ch5 - Perimeter Threat Mitigation (Headers & CORS)

- Book: book-1-foundations-core-security
- Chapter: ch5
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Spring Security

## Objective
Demonstrate how to harden an application's perimeter by emitting strict HTTP Security Response Headers (HSTS, CSP) and enforcing restrictive Cross-Origin Resource Sharing (CORS) policies.

## Security Controls Demonstrated
- Control family: `THREAT` (Threat Mitigation / Browser Defenses)
- **HTTP Security Headers:** 
  - *Anti-Pattern:* Relying solely on the frontend to protect itself from Cross-Site Scripting (XSS) or Clickjacking.
  - *Secure Fix:* The backend explicitly instructs the browser on how to behave securely using `SecurityFilterChain`:
    - `Strict-Transport-Security` (HSTS): Forces all subsequent traffic over HTTPS.
    - `X-Frame-Options: DENY`: Prevents the site from being embedded in an iframe (Clickjacking defense).
    - `Content-Security-Policy`: A strict `default-src 'self'` policy neuters XSS by forbidding inline scripts or remote script loading.
- **Strict CORS Policy:**
  - *Anti-Pattern:* A developer encounters a CORS error during development and adds `@CrossOrigin("*")` or explicitly configures `.setAllowedOrigins(List.of("*"))` alongside `.setAllowCredentials(true)`. This allows any malicious site on the internet to read sensitive data using the victim's ambient cookies/credentials.
  - *Secure Fix:* `CorsConfig.java` explicitly lists the exact, trusted frontend origins (e.g., `https://trusted-frontend.example.com`). Wildcards are forbidden when credentials are in play.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../CorsConfig.java`: Defines the restrictive `CorsConfigurationSource` bean.
- `src/main/java/.../SecurityConfig.java`: Wires up CORS and comprehensively configures the HTTP Security Headers.
- `src/main/java/.../DemoController.java`: A basic target to test header injection against.
- `src/test/java/.../SecurityHeadersTest.java`: Uses `MockMvc` to verify the presence of the exact headers (CSP, HSTS) and writes simulated CORS `OPTIONS` preflight requests to prove that trusted origins succeed while malicious ones are denied.
<!-- CODE_MAP_END -->

## Quick Start
You can build and test this project via Maven. 
- `mvn clean test`
