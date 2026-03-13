# ch5 - Request Validation Edge

- Book: book-1-foundations-core-security
- Chapter: ch5
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Spring Boot Starter Validation (JSR-380)

## Objective
Demonstrate strict input validation at the application boundary to drop malformed or malicious payloads before they ever reach business logic. Furthermore, demonstrate secure error handling to prevent verbose framework stack traces from leaking to potential attackers.

## Security Controls Demonstrated
- Control family: `THREAT` (Threat Avoidance) and `OBSERVABILITY` (Safe Logs/Errors)
- **Strict Input Validation (JSR-380):** 
  - *Anti-Pattern:* Controllers accept raw data or DTOs without `@Valid`. The service layer is forced to write messy `if (amount < 0)` checks, invariably missing edges cases like regex constraints against SQLi/XSS.
  - *Secure Fix:* Data Transfer Objects (DTOs) like `PaymentRequest` are heavily annotated with constraints (`@NotBlank`, `@Email`, `@Min`, `@Pattern`). The `DemoController` forces Spring to execute these rules using `@Valid` immediately at the network edge.
- **Secure Error Handling (`@RestControllerAdvice`):**
  - *Anti-Pattern:* Unhandled validation exceptions result in Spring returning a massive JSON structure containing Java stack traces and internal class names, providing attackers with a roadmap of the backend.
  - *Secure Fix:* `GlobalExceptionHandler` intercepts the `MethodArgumentNotValidException`, drops the stack trace, and constructs a clean, standardized `400 Bad Request` containing *only* the specific field feedback.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../PaymentRequest.java`: The DTO defining the strict validation boundaries, notably using `@Pattern` to restrict the character set of a free-text field.
- `src/main/java/.../DemoController.java`: Demonstrates the use of `@Valid @RequestBody` to erect the validation shield.
- `src/main/java/.../GlobalExceptionHandler.java`: Extracts the safe framework messages and hides the dangerous stack traces.
- `src/test/java/.../DemoControllerTest.java`: integration tests proving that negative amounts and malicious XSS strings are actively blocked with standard HTTP 400 responses.
<!-- CODE_MAP_END -->

## Quick Start
You can build and test this project via Maven. 
- `mvn clean test`
