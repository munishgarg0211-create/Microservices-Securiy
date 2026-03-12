# ch4 - Federation and Multitenancy Demo

- Book: book-1-foundations-core-security
- Chapter: ch4
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Spring Security (OAuth2 Resource Server)

## Objective
Demonstrate how to securely route and isolate data in a multi-tenant application where users authenticate via federated identity providers. This project illustrates extracting a custom `tenant_id` claim from a JWT and converting it into a Spring Security `GrantedAuthority` to enable robust, declarative access control.

## Mitigation Logic
- Control family: `AUTHZ` (Authorization and Access Control) / Multitenancy
- **Good practice:** Do not trust the client to specify their tenant boundary via HTTP headers or query parameters (e.g., `?tenant=acme`). Instead, cryptographically verify the tenant boundary by placing a `tenant_id` claim directly inside the trusted JWT payload issued by the Authorization Server.

## Key Concepts Demonstrated
1. **Custom JWT Converter (`TenantJwtAuthenticationConverter`)**: Intercepts the JWT processing pipeline to extract custom claims and map them to Spring Security Roles.
2. **Tenant Isolation (`@PreAuthorize("hasRole('TENANT_' + #tenantId.toUpperCase())")`)**: Using SpEL to dynamically verify that the caller's JWT has the correct tenant role before retrieving data for that specific tenant. 
3. **Mixed Role Support (`hasRole('ADMIN')`)**: Demonstrates maintaining standard global System Admin access rules alongside the custom federated tenant constraints.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoApplication.java`: Spring Boot entrypoint.
- `src/main/java/.../SecurityConfig.java`: Configures the app as an OAuth2 Resource Server and wires in the custom JWT converter.
- `src/main/java/.../TenantJwtAuthenticationConverter.java`: The core logic that transforms `{"tenant_id": "acme"}` into `ROLE_TENANT_ACME`.
- `src/main/java/.../DemoController.java`: Demonstrates tenant-isolated endpoints dynamically evaluating the bound role.
- `src/main/java/.../DemoService.java`: Service layer returning isolated payloads.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying strict tenant boundaries using standard `SecurityMockMvcRequestPostProcessors.jwt()` testing utilities.
<!-- CODE_MAP_END -->

## Quick Start
You can build and test this project via Maven. The JUnit integration tests perfectly illustrate the security matrix across different mock JWTs.
- `mvn clean test`
