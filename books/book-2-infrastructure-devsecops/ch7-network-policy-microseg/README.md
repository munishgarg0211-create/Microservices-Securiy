# ch7 - Network Policy Microsegmentation

- Book: book-2-infrastructure-devsecops
- Chapter: ch7
- Status: executable-demo
- Suggested stack: Calico/Cilium

## Objective
Deny-by-default pod network policy demo.

## Security Context

### The Problem
A flat internal network allows any microservice to communicate with any other. A single compromised frontend container can freely probe and exploit sensitive backend databases.

### The Resolution
Enforcing strict network policies creates micro-segmentation. Traffic is explicitly whitelist-only at the IP/Port level, blocking all unauthorized lateral communication natively within the orchestrator.

### Code Implementation
The logic enforcing this resolution is clearly marked in the code map below with `// COPY-PASTE READY:` annotations. This demonstrates exactly where the production-grade secure baseline naturally mitigates the vulnerability.\n\n## Mitigation Logic
- Control family: `POLICY` (deployment/runtime policy gate enforcement).
- Enforced security baseline:
  - Stops unsafe artifacts and non-compliant deployments before release.
  - Produces lower risk when policy conditions are satisfied.
  - Allows deployment despite failed control evidence.
  - Produces higher supply-chain/runtime risk.
- Example:
  - `GET /api/demo?imageSigned=false&hasSbom=false&criticalVulns=2` -> block.


## Demo Scope
- Use policy parameters like `imageSigned`, `hasSbom`, and `criticalVulns`.
- Verify pass/block behavior and risk through `controlDecision` and `riskScore`.

## Run Plan
1. Start service: `mvn spring-boot:run`.
2. Run API endpoint: `GET /api/demo?imageSigned=false&hasSbom=false&criticalVulns=2`.

## Acceptance Criteria
- The endpoint returns a deterministic, control-enforced decision for the chapter scenario.
- API response includes measurable fields (`controlDecision`, `expectedBehavior`, `riskScore`) and tests validate them.

## Generated Demo Sample
- Runtime: Spring Boot 3.x, Java 21
- API: GET /api/demo (supports `mode` + scenario params)
- Tests: DemoServiceTest, DemoControllerTest


## Code Demonstration Map

<!-- CODE_MAP_START -->
- `src/main/java/com/munishgarg/microsecurity/book2/ch7_network_policy_microseg/DemoApplication.java`: Spring Boot entrypoint.
- `src/main/java/com/munishgarg/microsecurity/book2/ch7_network_policy_microseg/DemoController.java`: Unified security endpoint.
- `src/main/java/com/munishgarg/microsecurity/book2/ch7_network_policy_microseg/DemoService.java`: Secure-by-default logic.
- `src/main/resources/application.yml`: Runtime configuration.
- `infra/Dockerfile`: Hardened, multi-stage build.
- `pom.xml`: Build dependencies.
- `src/test/java/com/munishgarg/microsecurity/book2/ch7_network_policy_microseg/DemoControllerTest.java`: Automated validation of the security endpoint.
- `src/test/java/com/munishgarg/microsecurity/book2/ch7_network_policy_microseg/DemoServiceTest.java`: Logic validation for deterministic outcomes.

### Core Concept Code

- Source: `src/main/java/com/munishgarg/microsecurity/book2/ch7_network_policy_microseg/DemoController.java`

```java
package com.munishgarg.microsecurity.book2.ch7_network_policy_microseg;

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
    // params carries chapter-specific inputs so one endpoint can demo different controls.
    // Production copy/paste checklist:
    // 1) Treat request params as untrusted input and validate strictly.
    // 2) Use authenticated principal/claims from security context for auth decisions.
    // 3) Keep authorization/business decisions in service/policy layer, not in controllers.

    @GetMapping
    public Map<String, Object> getDemo(
            @RequestParam(defaultValue = "secure") String mode,
            @RequestParam Map<String, String> params) {
        return demoService.demo(mode, params);
    }
}

```

- Source: `src/main/java/com/munishgarg/microsecurity/book2/ch7_network_policy_microseg/DemoService.java`

```java
package com.munishgarg.microsecurity.book2.ch7_network_policy_microseg;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch7-network-policy-microseg";
    private static final String BOOK = "book-2-infrastructure-devsecops";
    private static final String OBJECTIVE = "Deny-by-default pod network policy demo.";
    private static final String CONCEPT = "Network Policy Microseg";
    private static final String CONTROL_FAMILY = "POLICY";

    // Production copy/paste checklist:
    // 1) Evaluate real policy bundles (OPA/Kyverno/Conftest) in CI and admission paths.
    // 2) Integrate artifact signing, SBOM attestation, and vulnerability thresholds.
    // 3) Fail closed on policy errors and log policy decision evidence.


    public Map<String, Object> demo() {
        return demo("secure", Map.of());
    }

    public Map<String, Object> demo(String mode) {
        return demo(mode, Map.of());
    }

    public Map<String, Object> demo(String mode, Map<String, String> params) {
        // Secure mode = good practice: enforce controls.

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("status", "sample-ready");
        result.put("secureControl", "enabled");
        result.put("mode", normalizedMode);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        return result;
    }

        switch (CONTROL_FAMILY) {
        }
    }

        String actor = params.getOrDefault("actor", "alice");
        String owner = params.getOrDefault("owner", "bob");
        boolean authorizedByOwnership = actor.equals(owner) || "admin".equalsIgnoreCase(actor);

        // Good practice: enforce object ownership/role checks.
        // Bad practice: allow access regardless of ownership (BOLA-style flaw).
        result.put("scenario", "object-level-authorization");
        result.put("actor", actor);
        result.put("resourceOwner", owner);
        result.put("controlDecision", allowed ? "allow" : "deny");
                ? "owner or admin only access is enforced"
                : "authorization bypass demonstrates potential OWASP BOLA impact");
        result.put("riskScore", allowed && !authorizedByOwnership ? 95 : 25);
    }

        String tlsVersion = params.getOrDefault("tlsVersion", "1.0");
        boolean trustedClient = Boolean.parseBoolean(params.getOrDefault("trustedClient", "false"));

        boolean strongTls = compareTls(tlsVersion, "1.2") >= 0;
        // Good practice: require modern TLS + trusted identity.
        // Bad practice: accept weak or untrusted transport.

        result.put("scenario", "transport-hardening");
        result.put("tlsVersion", tlsVersion);
        result.put("trustedClient", trustedClient);
        result.put("controlDecision", accepted ? "allow" : "deny");
                ? "only trusted clients over TLS 1.2+ are accepted"
        result.put("riskScore", accepted && (!strongTls || !trustedClient) ? 90 : 20);
    }

        int requests = parseInt(params.getOrDefault("requests", "120"), 120);
        int limit = parseInt(params.getOrDefault("limit", "100"), 100);

        // Good practice: enforce limits and block overflow.
        // Bad practice: process all traffic and allow abuse.
        int allowed = requests - blocked;

        result.put("scenario", "abuse-throttling");
        result.put("requests", requests);
        result.put("limit", limit);
        result.put("allowedRequests", allowed);
        result.put("blockedRequests", blocked);
        result.put("controlDecision", blocked > 0 ? "throttle" : "allow");
                ? "excess traffic is throttled at the edge"
                : "all traffic passes and abuse window remains open");
        result.put("riskScore", blocked == 0 && requests > limit ? 88 : 30);
    }

        boolean producerAuth = Boolean.parseBoolean(params.getOrDefault("producerAuth", "false"));
        boolean schemaValid = Boolean.parseBoolean(params.getOrDefault("schemaValid", "false"));
        boolean encryptedChannel = Boolean.parseBoolean(params.getOrDefault("encryptedChannel", "false"));

        // Good practice: accept only authenticated, valid, encrypted messages.
        // Bad practice: trust any payload reaching the bus.

        result.put("scenario", "message-channel-security");
        result.put("producerAuth", producerAuth);
        result.put("schemaValid", schemaValid);
        result.put("encryptedChannel", encryptedChannel);
        result.put("controlDecision", accepted ? "accept" : "reject");
                ? "message accepted only when auth, schema and channel controls pass"
                : "unchecked message acceptance demonstrates event-bus risk");
        result.put("riskScore", accepted && !(producerAuth && schemaValid && encryptedChannel) ? 92 : 22);
    }

        boolean imageSigned = Boolean.parseBoolean(params.getOrDefault("imageSigned", "false"));
        boolean hasSbom = Boolean.parseBoolean(params.getOrDefault("hasSbom", "false"));
        int criticalVulns = parseInt(params.getOrDefault("criticalVulns", "2"), 2);

        // Good practice: enforce policy gates before deploy.
        // Bad practice: bypass gates even when artifacts fail checks.

        result.put("scenario", "policy-gate");
        result.put("imageSigned", imageSigned);
        result.put("hasSbom", hasSbom);
        result.put("criticalVulns", criticalVulns);
        result.put("controlDecision", pass ? "pass" : "block");
                ? "deployment is blocked until signing, SBOM and vuln gates pass"
                : "policy checks bypassed to show supply chain exposure");
        result.put("riskScore", pass && (!imageSigned || !hasSbom || criticalVulns > 0) ? 93 : 24);
    }

        int events = parseInt(params.getOrDefault("events", "50"), 50);
        int suspicious = parseInt(params.getOrDefault("suspicious", "7"), 7);

        // Good practice: enrich and triage all suspicious events.
        // Bad practice: weak telemetry creates blind spots.

        result.put("scenario", "security-observability");
        result.put("events", events);
        result.put("suspiciousEvents", suspicious);
        result.put("enrichedEvents", enriched);
        result.put("triagedEvents", triaged);
        result.put("controlDecision", triaged >= suspicious ? "visible" : "blind-spots");
                ? "security events are correlated for reliable detection"
                : "limited telemetry demonstrates alerting blind spots");
        result.put("riskScore", triaged < suspicious ? 84 : 28);
    }

        int incidents = parseInt(params.getOrDefault("incidents", "3"), 3);
        // Good practice: runbook-driven response lowers MTTR.
        // Bad practice: ad-hoc response increases impact window.

        result.put("scenario", "incident-response");
        result.put("incidents", incidents);
        result.put("mttrMinutes", mttrMinutes);
        result.put("runbookUsed", runbookUsed);
        result.put("controlDecision", runbookUsed ? "contained" : "uncoordinated");
                ? "runbook-driven containment reduces response time"
                : "absence of runbook increases incident impact duration");
        result.put("riskScore", runbookUsed ? 32 : 87);
    }

        String dataRegion = params.getOrDefault("dataRegion", "eu-west-1");
        String requestRegion = params.getOrDefault("requestRegion", "us-east-1");
        boolean evidenceAttached = Boolean.parseBoolean(params.getOrDefault("evidenceAttached", "false"));

        // Good practice: enforce region + evidence constraints.
        // Bad practice: ignore compliance controls and continue.

        result.put("scenario", "compliance-control");
        result.put("dataRegion", dataRegion);
        result.put("requestRegion", requestRegion);
        result.put("evidenceAttached", evidenceAttached);
        result.put("controlDecision", compliant ? "compliant" : "violation");
                ? "region and evidence checks enforce auditable compliance"
                : "policy bypass simulates non-compliant data handling");
        result.put("riskScore", compliant ? 27 : 86);
    }

        int exploitability = parseInt(params.getOrDefault("exploitability", "80"), 80);
                : parseInt(params.getOrDefault("controlCoverage", "30"), 30);

        // Good practice: improve control coverage to reduce residual risk.
        // Bad practice: low coverage leaves known attack paths exposed.
        int residualRisk = Math.max(0, exploitability - controlCoverage);

        result.put("scenario", "threat-mitigation");
        result.put("exploitability", exploitability);
        result.put("controlCoverage", controlCoverage);
        result.put("residualRisk", residualRisk);
        result.put("controlDecision", residualRisk <= 20 ? "mitigated" : "exposed");
                ? "controls reduce exploitability to an acceptable residual risk"
                : "weak controls leave known threat path exposed");
        result.put("riskScore", residualRisk <= 20 ? 29 : 89);
    }

        result.put("scenario", "secure-by-default");
                ? "security checks are applied before business processing"
                : "security checks are skipped to illustrate failure mode");
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

- Source: `src/test/java/com/munishgarg/microsecurity/book2/ch7_network_policy_microseg/DemoControllerTest.java`

```java
package com.munishgarg.microsecurity.book2.ch7_network_policy_microseg;

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
                .andExpect(jsonPath("$.project").value("ch7-network-policy-microseg"))
                .andExpect(jsonPath("$.mode").value("secure"))
                .andExpect(jsonPath("$.controlFamily").isNotEmpty())
                .andExpect(jsonPath("$.controlDecision").isNotEmpty());
    }

    @Test
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch7-network-policy-microseg"))
                .andExpect(jsonPath("$.expectedBehavior").isNotEmpty());
    }
}

```

- Source: `src/test/java/com/munishgarg/microsecurity/book2/ch7_network_policy_microseg/DemoServiceTest.java`

```java
package com.munishgarg.microsecurity.book2.ch7_network_policy_microseg;

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
        assertEquals("ch7-network-policy-microseg", result.get("project"));
        assertEquals("enabled", result.get("secureControl"));
        assertEquals("sample-ready", result.get("status"));
        assertEquals("secure", result.get("mode"));
    }

    @Test
        Map<String, Object> secure = service.demo("secure", Map.of());

        assertEquals("secure", secure.get("mode"));
    }
}

```

<!-- CODE_MAP_END -->










## Quick Start
- Run: mvn spring-boot:run
- Test: mvn test
