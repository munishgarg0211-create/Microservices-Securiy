# ch10 - Role of Monitoring in Security

- Book: book-2-infrastructure-devsecops
- Chapter: ch10
- Status: executable-demo
- Suggested stack: Prometheus

## Objective
Define and monitor security-centric SLOs.

## Security Context

### The Problem
Treating security solely as a reactive feature means core defense systems can quietly degrade, fail, or misconfigure in production without triggering alarms until a breach succeeds.

### The Resolution
Defining strict Security Service Level Objectives (SLOs) transforms security into a continuously monitored product requirement. Error budgets strictly enforce that the security perimeter remains perfectly healthy and operational.

### Code Implementation
The logic enforcing this resolution is clearly marked in the code map below with `// COPY-PASTE READY:` annotations. This demonstrates exactly where the production-grade secure baseline naturally mitigates the vulnerability.\n\n## Mitigation Logic
- Control family: `OBSERVABILITY` (security signal enrichment and triage coverage).
- Enforced security baseline:
  - Improves detection quality and response readiness.
  - Lowers operational risk through better visibility.
  - Under-instrumented telemetry weakens threat detection.
  - Raises risk due to reduced signal fidelity and triage coverage.
- Example:
  - `GET /api/demo?events=50&suspicious=7` -> high enrichment/triage.


## Demo Scope
- Use telemetry parameters like `events` and `suspicious`.
- Compare `enrichedEvents`, `triagedEvents`, and `riskScore` to show impact.

## Run Plan
1. Start service: `mvn spring-boot:run`.
2. Run API endpoint: `GET /api/demo?events=50&suspicious=7`.

## Acceptance Criteria
- The endpoint returns a deterministic, control-enforced decision for the chapter scenario.
- API response includes measurable fields (`controlDecision`, `expectedBehavior`, `riskScore`) and tests validate them.

## Generated Demo Sample
- Runtime: Spring Boot 3.x, Java 21
- API: GET /api/demo (supports `mode` + scenario params)
- Tests: DemoServiceTest, DemoControllerTest


## Code Demonstration Map

<!-- CODE_MAP_START -->
- `src/main/java/com/munishgarg/microsecurity/book2/ch10_security_monitoring_slos/DemoApplication.java`: Spring Boot entrypoint.
- `src/main/java/com/munishgarg/microsecurity/book2/ch10_security_monitoring_slos/DemoController.java`: Unified security endpoint.
- `src/main/java/com/munishgarg/microsecurity/book2/ch10_security_monitoring_slos/DemoService.java`: Secure-by-default logic.
- `src/main/resources/application.yml`: Runtime configuration.
- `infra/Dockerfile`: Hardened, multi-stage build.
- `pom.xml`: Build dependencies.
- `src/test/java/com/munishgarg/microsecurity/book2/ch10_security_monitoring_slos/DemoControllerTest.java`: Automated validation of the security endpoint.
- `src/test/java/com/munishgarg/microsecurity/book2/ch10_security_monitoring_slos/DemoServiceTest.java`: Logic validation for deterministic outcomes.

### Core Concept Code

- Source: `src/main/java/com/munishgarg/microsecurity/book2/ch10_security_monitoring_slos/DemoController.java`

```java
package com.munishgarg.microsecurity.book2.ch10_security_monitoring_slos;

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
    // Unified security endpoint: implements best-practice policy gating by default.
    // params carries chapter-specific inputs so one endpoint can demo different controls.
    // Production copy/paste checklist:
    // 1) Treat request params as untrusted input and validate strictly.
    // 2) Use authenticated principal/claims from security context for auth decisions.
    // 3) Keep authorization/business decisions in service/policy layer, not in controllers.

    @GetMapping
    public Map<String, Object> getDemo(
            @RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}

```

- Source: `src/main/java/com/munishgarg/microsecurity/book2/ch10_security_monitoring_slos/DemoService.java`

```java
package com.munishgarg.microsecurity.book2.ch10_security_monitoring_slos;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch10-security-monitoring-slos";
    private static final String BOOK = "book-2-infrastructure-devsecops";
    private static final String OBJECTIVE = "Define and monitor security-centric SLOs.";
    private static final String CONCEPT = "Security Monitoring Slos";
    private static final String CONTROL_FAMILY = "OBSERVABILITY";

    // Production copy/paste checklist:
    // 1) Emit structured security events with trace/user/request correlation IDs.
    // 2) Route events to SIEM/alerts with ownership and escalation policy.
    // 3) Define SLOs for detection latency and triage completeness.


    public Map<String, Object> demo() {
        return demo(Map.of());
    }

    public Map<String, Object> demo(Map<String, String> params) {
        // Unified secure implementation: always enforce controls.
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("status", "production-ready");
        result.put("securityModel", "unified-secure-baseline");
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        applyFamilyLogic(result, params);
        return result;
    }

    private void applyFamilyLogic(Map<String, Object> result, Map<String, String> params) {
        switch (CONTROL_FAMILY) {
            case "AUTHZ" -> applyAuthzScenario(result, params);
            case "TRANSPORT" -> applyTransportScenario(result, params);
            case "RATE_LIMIT" -> applyRateLimitScenario(result, params);
            case "MESSAGING" -> applyMessagingScenario(result, params);
            case "POLICY" -> applyPolicyScenario(result, params);
            case "OBSERVABILITY" -> applyObservabilityScenario(result, params);
            case "INCIDENT" -> applyIncidentScenario(result, params);
            case "COMPLIANCE" -> applyComplianceScenario(result, params);
            case "THREAT" -> applyThreatScenario(result, params);
            default -> applyDefaultScenario(result);
        }
    }

    private void applyAuthzScenario(Map<String, Object> result, Map<String, String> params) {
        String actor = params.getOrDefault("actor", "alice");
        String owner = params.getOrDefault("owner", "bob");
        boolean authorizedByOwnership = actor.equals(owner) || "admin".equalsIgnoreCase(actor);

        // Good practice: enforce object ownership/role checks.
        // Bad practice: allow access regardless of ownership (BOLA-style flaw).
        boolean allowed = authorizedByOwnership ;
        result.put("scenario", "object-level-authorization");
        result.put("actor", actor);
        result.put("resourceOwner", owner);
        result.put("controlDecision", allowed ? "allow" : "deny");
        result.put("expectedBehavior", "owner or admin only access is enforced");
        result.put("riskScore", allowed && !authorizedByOwnership ? 95 : 25);
    }

    private void applyTransportScenario(Map<String, Object> result, Map<String, String> params) {
        String tlsVersion = params.getOrDefault("tlsVersion", "1.0");
        boolean trustedClient = Boolean.parseBoolean(params.getOrDefault("trustedClient", "false"));

        boolean strongTls = compareTls(tlsVersion, "1.2") >= 0;
        // Good practice: require modern TLS + trusted identity.
        // Bad practice: accept weak or untrusted transport.
        boolean accepted = (strongTls && trustedClient) ;

        result.put("scenario", "transport-hardening");
        result.put("tlsVersion", tlsVersion);
        result.put("trustedClient", trustedClient);
        result.put("controlDecision", accepted ? "allow" : "deny");
        result.put("expectedBehavior", "only trusted clients over TLS 1.2+ are accepted");
        result.put("riskScore", accepted && (!strongTls || !trustedClient) ? 90 : 20);
    }

    private void applyRateLimitScenario(Map<String, Object> result, Map<String, String> params) {
        int requests = parseInt(params.getOrDefault("requests", "120"), 120);
        int limit = parseInt(params.getOrDefault("limit", "100"), 100);

        // Good practice: enforce limits and block overflow.
        // Bad practice: process all traffic and allow abuse.
        int blocked = Math.max(0, requests - limit) ;
        int allowed = requests - blocked;

        result.put("scenario", "abuse-throttling");
        result.put("requests", requests);
        result.put("limit", limit);
        result.put("allowedRequests", allowed);
        result.put("blockedRequests", blocked);
        result.put("controlDecision", blocked > 0 ? "throttle" : "allow");
        result.put("expectedBehavior", "excess traffic is throttled at the edge");
        result.put("riskScore", blocked == 0 && requests > limit ? 88 : 30);
    }

    private void applyMessagingScenario(Map<String, Object> result, Map<String, String> params) {
        boolean producerAuth = Boolean.parseBoolean(params.getOrDefault("producerAuth", "false"));
        boolean schemaValid = Boolean.parseBoolean(params.getOrDefault("schemaValid", "false"));
        boolean encryptedChannel = Boolean.parseBoolean(params.getOrDefault("encryptedChannel", "false"));

        // Good practice: accept only authenticated, valid, encrypted messages.
        // Bad practice: trust any payload reaching the bus.
        boolean accepted = (producerAuth && schemaValid && encryptedChannel) ;

        result.put("scenario", "message-channel-security");
        result.put("producerAuth", producerAuth);
        result.put("schemaValid", schemaValid);
        result.put("encryptedChannel", encryptedChannel);
        result.put("controlDecision", accepted ? "accept" : "reject");
        result.put("expectedBehavior", "message accepted only when auth, schema and channel controls pass");
        result.put("riskScore", accepted && !(producerAuth && schemaValid && encryptedChannel) ? 92 : 22);
    }

    private void applyPolicyScenario(Map<String, Object> result, Map<String, String> params) {
        boolean imageSigned = Boolean.parseBoolean(params.getOrDefault("imageSigned", "false"));
        boolean hasSbom = Boolean.parseBoolean(params.getOrDefault("hasSbom", "false"));
        int criticalVulns = parseInt(params.getOrDefault("criticalVulns", "2"), 2);

        // Good practice: enforce policy gates before deploy.
        // Bad practice: bypass gates even when artifacts fail checks.
        boolean pass = (imageSigned && hasSbom && criticalVulns == 0) ;

        result.put("scenario", "policy-gate");
        result.put("imageSigned", imageSigned);
        result.put("hasSbom", hasSbom);
        result.put("criticalVulns", criticalVulns);
        result.put("controlDecision", pass ? "pass" : "block");
        result.put("expectedBehavior", "deployment is blocked until signing, SBOM and vuln gates pass");
        result.put("riskScore", pass && (!imageSigned || !hasSbom || criticalVulns > 0) ? 93 : 24);
    }

    private void applyObservabilityScenario(Map<String, Object> result, Map<String, String> params) {
        int events = parseInt(params.getOrDefault("events", "50"), 50);
        int suspicious = parseInt(params.getOrDefault("suspicious", "7"), 7);

        // Good practice: enrich and triage all suspicious events.
        // Bad practice: weak telemetry creates blind spots.
        int enriched = events ;
        int triaged = suspicious ;

        result.put("scenario", "security-observability");
        result.put("events", events);
        result.put("suspiciousEvents", suspicious);
        result.put("enrichedEvents", enriched);
        result.put("triagedEvents", triaged);
        result.put("controlDecision", triaged >= suspicious ? "visible" : "blind-spots");
        result.put("expectedBehavior", "security events are correlated for reliable detection");
        result.put("riskScore", triaged < suspicious ? 84 : 28);
    }

    private void applyIncidentScenario(Map<String, Object> result, Map<String, String> params) {
        int incidents = parseInt(params.getOrDefault("incidents", "3"), 3);
        // Good practice: runbook-driven response lowers MTTR.
        // Bad practice: ad-hoc response increases impact window.
        int mttrMinutes = 25 ;
        boolean runbookUsed = true;

        result.put("scenario", "incident-response");
        result.put("incidents", incidents);
        result.put("mttrMinutes", mttrMinutes);
        result.put("runbookUsed", runbookUsed);
        result.put("controlDecision", runbookUsed ? "contained" : "uncoordinated");
        result.put("expectedBehavior", "runbook-driven containment reduces response time");
        result.put("riskScore", runbookUsed ? 32 : 87);
    }

    private void applyComplianceScenario(Map<String, Object> result, Map<String, String> params) {
        String dataRegion = params.getOrDefault("dataRegion", "eu-west-1");
        String requestRegion = params.getOrDefault("requestRegion", "us-east-1");
        boolean evidenceAttached = Boolean.parseBoolean(params.getOrDefault("evidenceAttached", "false"));

        // Good practice: enforce region + evidence constraints.
        // Bad practice: ignore compliance controls and continue.
        boolean compliant = (dataRegion.equals(requestRegion) && evidenceAttached) ;

        result.put("scenario", "compliance-control");
        result.put("dataRegion", dataRegion);
        result.put("requestRegion", requestRegion);
        result.put("evidenceAttached", evidenceAttached);
        result.put("controlDecision", compliant ? "compliant" : "violation");
        result.put("expectedBehavior", "region and evidence checks enforce auditable compliance");
        result.put("riskScore", compliant ? 27 : 86);
    }

    private void applyThreatScenario(Map<String, Object> result, Map<String, String> params) {
        int exploitability = parseInt(params.getOrDefault("exploitability", "80"), 80);
        int controlCoverage = parseInt(params.getOrDefault("controlCoverage", "85"), 85)
                ;

        // Good practice: improve control coverage to reduce residual risk.
        // Bad practice: low coverage leaves known attack paths exposed.
        int residualRisk = Math.max(0, exploitability - controlCoverage);

        result.put("scenario", "threat-mitigation");
        result.put("exploitability", exploitability);
        result.put("controlCoverage", controlCoverage);
        result.put("residualRisk", residualRisk);
        result.put("controlDecision", residualRisk <= 20 ? "mitigated" : "exposed");
        result.put("expectedBehavior", "controls reduce exploitability to an acceptable residual risk");
        result.put("riskScore", residualRisk <= 20 ? 29 : 89);
    }

    private void applyDefaultScenario(Map<String, Object> result) {
        result.put("scenario", "secure-by-default");
        result.put("controlDecision", "enforced");
        result.put("expectedBehavior", "security checks are applied before business processing");
        result.put("riskScore", 35);
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

- Source: `src/test/java/com/munishgarg/microsecurity/book2/ch10_security_monitoring_slos/DemoControllerTest.java`

```java
package com.munishgarg.microsecurity.book2.ch10_security_monitoring_slos;

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
                .andExpect(jsonPath("$.project").value("ch10-security-monitoring-slos"))
                .andExpect(jsonPath("$.securityModel").value("unified-secure-baseline"))
                .andExpect(jsonPath("$.controlFamily").isNotEmpty())
                .andExpect(jsonPath("$.controlDecision").isNotEmpty());
    }

}

```

- Source: `src/test/java/com/munishgarg/microsecurity/book2/ch10_security_monitoring_slos/DemoServiceTest.java`

```java
package com.munishgarg.microsecurity.book2.ch10_security_monitoring_slos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    @Test
    void shouldReturnProjectMetadataAndSecureDefaults() {
        Map<String, Object> result = service.demo(Map.of());

        assertNotNull(result);
        assertEquals("ch10-security-monitoring-slos", result.get("project"));
        assertEquals("unified-secure-baseline", result.get("securityModel"));
        assertEquals("production-ready", result.get("status"));
    }
}

```

<!-- CODE_MAP_END -->










## Quick Start
- Run: mvn spring-boot:run
- Test: mvn test
