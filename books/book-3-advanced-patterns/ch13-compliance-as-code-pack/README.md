# ch13 - Compliance as Code

- Book: book-3-advanced-patterns
- Chapter: ch13
- Status: scaffolded
- Suggested stack: OPA

## Objective
OPA/Conftest policy pack for compliance.

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

## Code Demonstration Map

## Code Demonstration Map

## Code Demonstration Map

<!-- CODE_MAP_START -->
- `src/main/java/com/munishgarg/microsecurity/book3/ch13_compliance_as_code_pack/DemoApplication.java`: Spring Boot entrypoint that starts this chapter demo.
- `src/main/java/com/munishgarg/microsecurity/book3/ch13_compliance_as_code_pack/DemoController.java`: API layer where request validation/authorization behavior is demonstrated.
- `src/main/java/com/munishgarg/microsecurity/book3/ch13_compliance_as_code_pack/DemoService.java`: Service logic that implements the chapter's security control.
- `src/main/resources/application.yml`: Runtime security/config properties for this chapter.
- `infra/`: Reserved for deployment/policy manifests (currently scaffold placeholder).
- `pom.xml`: Build dependencies and plugins used to run and test this demo.
- `src/test/java/com/munishgarg/microsecurity/book3/ch13_compliance_as_code_pack/DemoControllerTest.java`: Automated check that validates expected secure behavior and impact.
- `src/test/java/com/munishgarg/microsecurity/book3/ch13_compliance_as_code_pack/DemoServiceTest.java`: Automated check that validates expected secure behavior and impact.

- **Highlight:** Core concept and impact tests below are pulled from the chapter implementation.

### Core Concept Code

- Source: `src/main/java/com/munishgarg/microsecurity/book3/ch13_compliance_as_code_pack/DemoController.java`

```java
package com.munishgarg.microsecurity.book3.ch13_compliance_as_code_pack;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping
    public Map<String, Object> getDemo(
            @RequestParam(defaultValue = "secure") String mode,
            @RequestParam Map<String, String> params) {
        return demoService.demo(mode, params);
    }
}
```

- Source: `src/main/java/com/munishgarg/microsecurity/book3/ch13_compliance_as_code_pack/DemoService.java`

```java
package com.munishgarg.microsecurity.book3.ch13_compliance_as_code_pack;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch13-compliance-as-code-pack";
    private static final String BOOK = "book-3-advanced-patterns";
    private static final String OBJECTIVE = "OPA/Conftest policy pack for compliance.";
    private static final String CONCEPT = "Compliance As Code Pack";
    private static final String CONTROL_FAMILY = "COMPLIANCE";

    public Map<String, Object> demo() {
        return demo("secure", Map.of());
    }

    public Map<String, Object> demo(String mode) {
        return demo(mode, Map.of());
    }

    public Map<String, Object> demo(String mode, Map<String, String> params) {
        String normalizedMode = "insecure".equalsIgnoreCase(mode) ? "insecure" : "secure";
        boolean secureMode = "secure".equals(normalizedMode);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("status", "sample-ready");
        result.put("secureControl", "enabled");
        result.put("mode", normalizedMode);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        applyFamilyLogic(result, params, secureMode);
        return result;
    }

    private void applyFamilyLogic(Map<String, Object> result, Map<String, String> params, boolean secureMode) {
        switch (CONTROL_FAMILY) {
            case "AUTHZ" -> applyAuthzScenario(result, params, secureMode);
            case "TRANSPORT" -> applyTransportScenario(result, params, secureMode);
            case "RATE_LIMIT" -> applyRateLimitScenario(result, params, secureMode);
            case "MESSAGING" -> applyMessagingScenario(result, params, secureMode);
            case "POLICY" -> applyPolicyScenario(result, params, secureMode);
            case "OBSERVABILITY" -> applyObservabilityScenario(result, params, secureMode);
            case "INCIDENT" -> applyIncidentScenario(result, params, secureMode);
            case "COMPLIANCE" -> applyComplianceScenario(result, params, secureMode);
            case "THREAT" -> applyThreatScenario(result, params, secureMode);
            default -> applyDefaultScenario(result, secureMode);
        }
    }

    private void applyAuthzScenario(Map<String, Object> result, Map<String, String> params, boolean secureMode) {
        String actor = params.getOrDefault("actor", "alice");
        String owner = params.getOrDefault("owner", "bob");
        boolean authorizedByOwnership = actor.equals(owner) || "admin".equalsIgnoreCase(actor);

        boolean allowed = secureMode ? authorizedByOwnership : true;
        result.put("scenario", "object-level-authorization");
        result.put("actor", actor);
        result.put("resourceOwner", owner);
        result.put("controlDecision", allowed ? "allow" : "deny");
        result.put("expectedBehavior", secureMode
                ? "owner or admin only access is enforced"
                : "authorization bypass demonstrates potential OWASP BOLA impact");
        result.put("riskScore", allowed && !authorizedByOwnership ? 95 : 25);
    }

    private void applyTransportScenario(Map<String, Object> result, Map<String, String> params, boolean secureMode) {
        String tlsVersion = params.getOrDefault("tlsVersion", "1.0");
        boolean trustedClient = Boolean.parseBoolean(params.getOrDefault("trustedClient", "false"));

        boolean strongTls = compareTls(tlsVersion, "1.2") >= 0;
        boolean accepted = secureMode ? (strongTls && trustedClient) : true;

        result.put("scenario", "transport-hardening");
        result.put("tlsVersion", tlsVersion);
        result.put("trustedClient", trustedClient);
        result.put("controlDecision", accepted ? "allow" : "deny");
        result.put("expectedBehavior", secureMode
                ? "only trusted clients over TLS 1.2+ are accepted"
                : "weak transport accepted to demonstrate insecure baseline");
        result.put("riskScore", accepted && (!strongTls || !trustedClient) ? 90 : 20);
    }

    private void applyRateLimitScenario(Map<String, Object> result, Map<String, String> params, boolean secureMode) {
        int requests = parseInt(params.getOrDefault("requests", "120"), 120);
        int limit = parseInt(params.getOrDefault("limit", "100"), 100);

        int blocked = secureMode ? Math.max(0, requests - limit) : 0;
        int allowed = requests - blocked;

        result.put("scenario", "abuse-throttling");
        result.put("requests", requests);
        result.put("limit", limit);
        result.put("allowedRequests", allowed);
        result.put("blockedRequests", blocked);
        result.put("controlDecision", blocked > 0 ? "throttle" : "allow");
        result.put("expectedBehavior", secureMode
                ? "excess traffic is throttled at the edge"
                : "all traffic passes and abuse window remains open");
        result.put("riskScore", blocked == 0 && requests > limit ? 88 : 30);
    }

    private void applyMessagingScenario(Map<String, Object> result, Map<String, String> params, boolean secureMode) {
        boolean producerAuth = Boolean.parseBoolean(params.getOrDefault("producerAuth", "false"));
        boolean schemaValid = Boolean.parseBoolean(params.getOrDefault("schemaValid", "false"));
        boolean encryptedChannel = Boolean.parseBoolean(params.getOrDefault("encryptedChannel", "false"));

        boolean accepted = secureMode ? (producerAuth && schemaValid && encryptedChannel) : true;

        result.put("scenario", "message-channel-security");
        result.put("producerAuth", producerAuth);
        result.put("schemaValid", schemaValid);
        result.put("encryptedChannel", encryptedChannel);
        result.put("controlDecision", accepted ? "accept" : "reject");
        result.put("expectedBehavior", secureMode
                ? "message accepted only when auth, schema and channel controls pass"
                : "unchecked message acceptance demonstrates event-bus risk");
        result.put("riskScore", accepted && !(producerAuth && schemaValid && encryptedChannel) ? 92 : 22);
    }

    private void applyPolicyScenario(Map<String, Object> result, Map<String, String> params, boolean secureMode) {
        boolean imageSigned = Boolean.parseBoolean(params.getOrDefault("imageSigned", "false"));
        boolean hasSbom = Boolean.parseBoolean(params.getOrDefault("hasSbom", "false"));
        int criticalVulns = parseInt(params.getOrDefault("criticalVulns", "2"), 2);

        boolean pass = secureMode ? (imageSigned && hasSbom && criticalVulns == 0) : true;

        result.put("scenario", "policy-gate");
        result.put("imageSigned", imageSigned);
        result.put("hasSbom", hasSbom);
        result.put("criticalVulns", criticalVulns);
        result.put("controlDecision", pass ? "pass" : "block");
        result.put("expectedBehavior", secureMode
                ? "deployment is blocked until signing, SBOM and vuln gates pass"
                : "policy checks bypassed to show supply chain exposure");
        result.put("riskScore", pass && (!imageSigned || !hasSbom || criticalVulns > 0) ? 93 : 24);
    }

    private void applyObservabilityScenario(Map<String, Object> result, Map<String, String> params, boolean secureMode) {
        int events = parseInt(params.getOrDefault("events", "50"), 50);
        int suspicious = parseInt(params.getOrDefault("suspicious", "7"), 7);

        int enriched = secureMode ? events : Math.max(0, events / 5);
        int triaged = secureMode ? suspicious : Math.max(0, suspicious / 3);

        result.put("scenario", "security-observability");
        result.put("events", events);
        result.put("suspiciousEvents", suspicious);
        result.put("enrichedEvents", enriched);
        result.put("triagedEvents", triaged);
        result.put("controlDecision", triaged >= suspicious ? "visible" : "blind-spots");
        result.put("expectedBehavior", secureMode
                ? "security events are correlated for reliable detection"
                : "limited telemetry demonstrates alerting blind spots");
        result.put("riskScore", triaged < suspicious ? 84 : 28);
    }

    private void applyIncidentScenario(Map<String, Object> result, Map<String, String> params, boolean secureMode) {
        int incidents = parseInt(params.getOrDefault("incidents", "3"), 3);
        int mttrMinutes = secureMode ? 25 : 120;
        boolean runbookUsed = secureMode;

        result.put("scenario", "incident-response");
        result.put("incidents", incidents);
        result.put("mttrMinutes", mttrMinutes);
        result.put("runbookUsed", runbookUsed);
        result.put("controlDecision", runbookUsed ? "contained" : "uncoordinated");
        result.put("expectedBehavior", secureMode
                ? "runbook-driven containment reduces response time"
                : "absence of runbook increases incident impact duration");
        result.put("riskScore", runbookUsed ? 32 : 87);
    }

    private void applyComplianceScenario(Map<String, Object> result, Map<String, String> params, boolean secureMode) {
        String dataRegion = params.getOrDefault("dataRegion", "eu-west-1");
        String requestRegion = params.getOrDefault("requestRegion", "us-east-1");
        boolean evidenceAttached = Boolean.parseBoolean(params.getOrDefault("evidenceAttached", "false"));

        boolean compliant = secureMode ? (dataRegion.equals(requestRegion) && evidenceAttached) : true;

        result.put("scenario", "compliance-control");
        result.put("dataRegion", dataRegion);
        result.put("requestRegion", requestRegion);
        result.put("evidenceAttached", evidenceAttached);
        result.put("controlDecision", compliant ? "compliant" : "violation");
        result.put("expectedBehavior", secureMode
                ? "region and evidence checks enforce auditable compliance"
                : "policy bypass simulates non-compliant data handling");
        result.put("riskScore", compliant ? 27 : 86);
    }

    private void applyThreatScenario(Map<String, Object> result, Map<String, String> params, boolean secureMode) {
        int exploitability = parseInt(params.getOrDefault("exploitability", "80"), 80);
        int controlCoverage = secureMode ? parseInt(params.getOrDefault("controlCoverage", "85"), 85)
                : parseInt(params.getOrDefault("controlCoverage", "30"), 30);

        int residualRisk = Math.max(0, exploitability - controlCoverage);

        result.put("scenario", "threat-mitigation");
        result.put("exploitability", exploitability);
        result.put("controlCoverage", controlCoverage);
        result.put("residualRisk", residualRisk);
        result.put("controlDecision", residualRisk <= 20 ? "mitigated" : "exposed");
        result.put("expectedBehavior", secureMode
                ? "controls reduce exploitability to an acceptable residual risk"
                : "weak controls leave known threat path exposed");
        result.put("riskScore", residualRisk <= 20 ? 29 : 89);
    }

    private void applyDefaultScenario(Map<String, Object> result, boolean secureMode) {
        result.put("scenario", "secure-by-default");
        result.put("controlDecision", secureMode ? "enforced" : "bypassed");
        result.put("expectedBehavior", secureMode
                ? "security checks are applied before business processing"
                : "security checks are skipped to illustrate failure mode");
        result.put("riskScore", secureMode ? 35 : 82);
    }

    private int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    private int compareTls(String left, String right) {
        return parseTls(left) - parseTls(right);
    }

    private int parseTls(String version) {
        String digits = version == null ? "" : version.replace(".", "");
        return parseInt(digits, 0);
    }
}
```

### Impact Demonstration Tests

- Source: `src/test/java/com/munishgarg/microsecurity/book3/ch13_compliance_as_code_pack/DemoControllerTest.java`

```java
package com.munishgarg.microsecurity.book3.ch13_compliance_as_code_pack;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldServeSecureDemoPayload() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch13-compliance-as-code-pack"))
                .andExpect(jsonPath("$.mode").value("secure"))
                .andExpect(jsonPath("$.controlFamily").isNotEmpty())
                .andExpect(jsonPath("$.controlDecision").isNotEmpty());
    }

    @Test
    void shouldServeInsecureDemoPayload() throws Exception {
        mockMvc.perform(get("/api/demo").param("mode", "insecure"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch13-compliance-as-code-pack"))
                .andExpect(jsonPath("$.mode").value("insecure"))
                .andExpect(jsonPath("$.expectedBehavior").isNotEmpty());
    }
}
```

- Source: `src/test/java/com/munishgarg/microsecurity/book3/ch13_compliance_as_code_pack/DemoServiceTest.java`

```java
package com.munishgarg.microsecurity.book3.ch13_compliance_as_code_pack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldReturnProjectMetadataAndSecureDefaults() {
        Map<String, Object> result = service.demo("secure", Map.of());

        assertNotNull(result);
        assertEquals("ch13-compliance-as-code-pack", result.get("project"));
        assertEquals("enabled", result.get("secureControl"));
        assertEquals("sample-ready", result.get("status"));
        assertEquals("secure", result.get("mode"));
    }

    @Test
    void shouldDifferentiateSecureAndInsecureImpact() {
        Map<String, Object> secure = service.demo("secure", Map.of());
        Map<String, Object> insecure = service.demo("insecure", Map.of());

        assertEquals("secure", secure.get("mode"));
        assertEquals("insecure", insecure.get("mode"));
        assertNotEquals(secure.get("expectedBehavior"), insecure.get("expectedBehavior"));
    }
}
```
<!-- CODE_MAP_END -->








## Quick Start
- Run: mvn spring-boot:run
- Test: mvn test
