# ch4 - Authorization Anti-Patterns Lab

- Book: book-1-foundations-core-security
- Chapter: ch4
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Spring Security

## Objective
Demonstrate dangerous but common authorization bad practices alongside their corrections. This project specifically highlights Broken Object Level Authorization (BOLA) and Missing Function Level Access Control (MFLAC).

## Vulnerabilities Demonstrated
- Control family: `THREAT` (Threat Avoidance) 
- **BOLA (Broken Object Level Authorization):** 
  - *Anti-Pattern:* An endpoint fetches an object simply by trusting an ID passed in the URL (e.g., `/vulnerable/data/bob`) without verifying if the authenticated user (`alice`) actually owns or has rights to `bob`'s data.
  - *Secure Fix:* Applying `@PreAuthorize("#userId == authentication.name")` to ensure the entity requested matches the cryptographic identity of the active request.
- **MFLAC (Missing Function Level Access Control):**
  - *Anti-Pattern:* Administrative or sensitive functions exist without explicit role guards because the developer assumes "the UI doesn't show the link to normal users, so they won't find it."
  - *Secure Fix:* Explicitly bounding administrative endpoints with `@PreAuthorize("hasRole('ADMIN')")`.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoApplication.java`: Spring Boot entrypoint.
- `src/main/java/.../SecurityConfig.java`: Configures basic In-Memory users (`alice`, `bob`, `admin`) to interact with the lab.
- `src/main/java/.../DemoController.java`: A side-by-side comparison of vulnerable endpoints versus securely annotated endpoints.
- `src/main/java/.../DemoService.java`: Data stubs providing the mock resources.
- `src/test/java/.../DemoControllerTest.java`: Contains explicit exploit scenarios written as JUnit tests, proving that `alice` can successfully steal `bob`'s data on the vulnerable port, but is blocked on the secure port.
<!-- CODE_MAP_END -->

## Quick Start
You can build and test this project via Maven. The JUnit integration tests perfectly illustrate the exploits.
- `mvn clean test`
