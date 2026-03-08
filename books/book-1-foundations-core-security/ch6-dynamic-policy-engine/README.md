# ch6 - Dynamic Policy Configuration

- Book: book-1-foundations-core-security
- Chapter: ch6
- Status: scaffolded
- Suggested stack: OPA, Config Server

## Objective
Externalized runtime security policy updates.

## Demo Scope
- Build a minimal, runnable demo focused only on this concept.
- Include one insecure path and one secured/fixed path where applicable.
- Add automated checks or tests for the security behavior.

## Run Plan
1. Add service code under src/.
2. Add deployment/runtime config under infra/.
3. Add local run instructions and test commands.

## Acceptance Criteria
- Concept is demonstrable in isolation.
- Security control is measurable (logs, test, or policy decision).
- Failure mode and secure mode are both documented.

## Generated Demo Sample
- Runtime: Spring Boot 3.x, Java 21
- API: GET /api/demo
- Tests: DemoServiceTest, DemoControllerTest


## Code Demonstration Map

<!-- CODE_MAP_START -->
- `src/main/java/com/munishgarg/microsecurity/book1/ch6_dynamic_policy_engine/DemoApplication.java`: Spring Boot entrypoint that starts this chapter demo.
- `src/main/java/com/munishgarg/microsecurity/book1/ch6_dynamic_policy_engine/DemoController.java`: API layer where request validation/authorization behavior is demonstrated.
- `src/main/java/com/munishgarg/microsecurity/book1/ch6_dynamic_policy_engine/DemoService.java`: Service logic that implements the chapter's security control.
- `src/main/resources/application.yml`: Runtime security/config properties for this chapter.
- `infra/`: Reserved for deployment/policy manifests (currently scaffold placeholder).
- `pom.xml`: Build dependencies and plugins used to run and test this demo.
- `src/test/java/com/munishgarg/microsecurity/book1/ch6_dynamic_policy_engine/DemoControllerTest.java`: Automated check that validates expected secure behavior.
- `src/test/java/com/munishgarg/microsecurity/book1/ch6_dynamic_policy_engine/DemoServiceTest.java`: Automated check that validates expected secure behavior.

- **Highlight:** Core concept snippets below are pulled from the chapter's Java implementation.

### Core Concept Code

- Source: `src/main/java/com/munishgarg/microsecurity/book1/ch6_dynamic_policy_engine/DemoController.java`

```java
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping
    public Map<String, String> getDemo() {
        return demoService.demo();
    }
}
```

- Source: `src/main/java/com/munishgarg/microsecurity/book1/ch6_dynamic_policy_engine/DemoService.java`

```java
@Service
public class DemoService {
    public Map<String, String> demo() {
        return Map.of(
                "project", "ch6-dynamic-policy-engine",
                "book", "book-1-foundations-core-security",
                "status", "sample-ready",
                "secureControl", "enabled",
                "nextStep", "replace placeholder logic with chapter-specific implementation"
        );
    }
}
```
<!-- CODE_MAP_END -->


## Quick Start
- Run: mvn spring-boot:run
- Test: mvn test
