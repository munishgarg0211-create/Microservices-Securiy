# ch4 - RBAC and ABAC Demo

- Book: book-1-foundations-core-security
- Chapter: ch4
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Spring Security

## Objective
Demonstrate advanced authorization capabilities in a microservice using Spring Security's `@EnableMethodSecurity`. This project illustrates the difference between Role-Based Access Control (RBAC) and Attribute-Based Access Control (ABAC), preventing common vulnerabilities like Broken Object Level Authorization (BOLA).

## Mitigation Logic
- Control family: `AUTHZ` (Authorization and Access Control).
- **Good practice:** Don't put authorization logic inside business methods. Use powerful framework level interceptors like `@PreAuthorize` that fail-closed before the method is even executed.

## Key Concepts Demonstrated
1. **RBAC (`hasRole('ADMIN')`)**: Granting access to an endpoint globally based on the identity's assigned group or role.
2. **ABAC (`@docSecurity.isOwner(#docId, authentication.name)`)**: Granting access to a specific piece of data based on the *attributes* of both the user (their username) and the resource (its owner).

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoApplication.java`: Spring Boot entrypoint.
- `src/main/java/.../SecurityConfig.java`: Enables method security and mocks two in-memory users: `alice` (USER) and `admin` (ADMIN).
- `src/main/java/.../DocumentSecurityTracker.java`: A Spring Bean acting as a simulated database / Policy Decision Point that knows who owns which document.
- `src/main/java/.../DemoController.java`: Demonstrates securing endpoints with SpEL statements in `@PreAuthorize`.
- `src/main/java/.../DemoService.java`: Standard backend service.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying that Spring Security correctly blocks unauthorized users using `@WithMockUser`.
<!-- CODE_MAP_END -->

## Quick Start
You can build and test this project via Maven. The JUnit integration tests perfectly illustrate the security matrix.
- `mvn clean test`
