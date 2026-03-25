package com.munishgarg.microsecurity.book3.ch12_security_first_adr;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch12-security-first-adr";
    private static final String BOOK = "book-3-advanced-patterns";
    private static final String OBJECTIVE = "Security-focused architecture decision records.";
    private static final String CONCEPT = "Security First Adr";
    private static final String CONTROL_FAMILY = "COMPLIANCE";

    // Production copy/paste checklist:
    // 1) Source region/classification constraints from policy registry, not request input.
    // 2) Persist auditable decision artifacts (who/what/when/why).
    // 3) Enforce deny-by-default when evidence is missing or stale.


        // [COPY/PASTE] Unified security endpoint logic.
    public Map<String, Object> demo() {
        return demo(Map.of());
    }

    

        // [COPY/PASTE] Unified security endpoint logic.
    public Map<String, Object> demo(Map<String, String> params) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("status", "sample-ready");
        result.put("secureControl", "enabled");
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
        boolean allowed = authorizedByOwnership;
        result.put("scenario", "object-level-authorization");
        result.put("actor", actor);
        result.put("resourceOwner", owner);
        result.put("controlDecision", allowed ? "allow" : "deny");
        result.put("expectedBehavior", "Production security controls natively enforced.");
        result.put("riskScore", allowed && !authorizedByOwnership ? 95 : 25);
    }

    private void applyTransportScenario(Map<String, Object> result, Map<String, String> params) {
        String tlsVersion = params.getOrDefault("tlsVersion", "1.0");
        boolean trustedClient = Boolean.parseBoolean(params.getOrDefault("trustedClient", "false"));

        boolean strongTls = compareTls(tlsVersion, "1.2") >= 0;
        // Good practice: require modern TLS + trusted identity.
        // Bad practice: accept weak or untrusted transport.
        boolean accepted = (strongTls && trustedClient);

        result.put("scenario", "transport-hardening");
        result.put("tlsVersion", tlsVersion);
        result.put("trustedClient", trustedClient);
        result.put("controlDecision", accepted ? "allow" : "deny");
        result.put("expectedBehavior", "Production security controls natively enforced.");
        result.put("riskScore", accepted && (!strongTls || !trustedClient) ? 90 : 20);
    }

    private void applyRateLimitScenario(Map<String, Object> result, Map<String, String> params) {
        int requests = parseInt(params.getOrDefault("requests", "120"), 120);
        int limit = parseInt(params.getOrDefault("limit", "100"), 100);

        // Good practice: enforce limits and block overflow.
        // Bad practice: process all traffic and allow abuse.
        int blocked = Math.max(0, requests - limit);
        int allowed = requests - blocked;

        result.put("scenario", "abuse-throttling");
        result.put("requests", requests);
        result.put("limit", limit);
        result.put("allowedRequests", allowed);
        result.put("blockedRequests", blocked);
        result.put("controlDecision", blocked > 0 ? "throttle" : "allow");
        result.put("expectedBehavior", "Production security controls natively enforced.");
        result.put("riskScore", blocked == 0 && requests > limit ? 88 : 30);
    }

    private void applyMessagingScenario(Map<String, Object> result, Map<String, String> params) {
        boolean producerAuth = Boolean.parseBoolean(params.getOrDefault("producerAuth", "false"));
        boolean schemaValid = Boolean.parseBoolean(params.getOrDefault("schemaValid", "false"));
        boolean encryptedChannel = Boolean.parseBoolean(params.getOrDefault("encryptedChannel", "false"));

        // Good practice: accept only authenticated, valid, encrypted messages.
        // Bad practice: trust any payload reaching the bus.
        boolean accepted = (producerAuth && schemaValid && encryptedChannel);

        result.put("scenario", "message-channel-security");
        result.put("producerAuth", producerAuth);
        result.put("schemaValid", schemaValid);
        result.put("encryptedChannel", encryptedChannel);
        result.put("controlDecision", accepted ? "accept" : "reject");
        result.put("expectedBehavior", "Production security controls natively enforced.");
        result.put("riskScore", accepted && !(producerAuth && schemaValid && encryptedChannel) ? 92 : 22);
    }

    private void applyPolicyScenario(Map<String, Object> result, Map<String, String> params) {
        boolean imageSigned = Boolean.parseBoolean(params.getOrDefault("imageSigned", "false"));
        boolean hasSbom = Boolean.parseBoolean(params.getOrDefault("hasSbom", "false"));
        int criticalVulns = parseInt(params.getOrDefault("criticalVulns", "2"), 2);

        // Good practice: enforce policy gates before deploy.
        // Bad practice: bypass gates even when artifacts fail checks.
        boolean pass = (imageSigned && hasSbom && criticalVulns == 0);

        result.put("scenario", "policy-gate");
        result.put("imageSigned", imageSigned);
        result.put("hasSbom", hasSbom);
        result.put("criticalVulns", criticalVulns);
        result.put("controlDecision", pass ? "pass" : "block");
        result.put("expectedBehavior", "Production security controls natively enforced.");
        result.put("riskScore", pass && (!imageSigned || !hasSbom || criticalVulns > 0) ? 93 : 24);
    }

    private void applyObservabilityScenario(Map<String, Object> result, Map<String, String> params) {
        int events = parseInt(params.getOrDefault("events", "50"), 50);
        int suspicious = parseInt(params.getOrDefault("suspicious", "7"), 7);

        // Good practice: enrich and triage all suspicious events.
        // Bad practice: weak telemetry creates blind spots.
        int enriched = events;
        int triaged = suspicious;

        result.put("scenario", "security-observability");
        result.put("events", events);
        result.put("suspiciousEvents", suspicious);
        result.put("enrichedEvents", enriched);
        result.put("triagedEvents", triaged);
        result.put("controlDecision", triaged >= suspicious ? "visible" : "blind-spots");
        result.put("expectedBehavior", "Production security controls natively enforced.");
        result.put("riskScore", triaged < suspicious ? 84 : 28);
    }

    private void applyIncidentScenario(Map<String, Object> result, Map<String, String> params) {
        int incidents = parseInt(params.getOrDefault("incidents", "3"), 3);
        // Good practice: runbook-driven response lowers MTTR.
        // Bad practice: ad-hoc response increases impact window.
        int mttrMinutes = 25;
        boolean runbookUsed = true;

        result.put("scenario", "incident-response");
        result.put("incidents", incidents);
        result.put("mttrMinutes", mttrMinutes);
        result.put("runbookUsed", runbookUsed);
        result.put("controlDecision", runbookUsed ? "contained" : "uncoordinated");
        result.put("expectedBehavior", "Production security controls natively enforced.");
        result.put("riskScore", runbookUsed ? 32 : 87);
    }

    private void applyComplianceScenario(Map<String, Object> result, Map<String, String> params) {
        String dataRegion = params.getOrDefault("dataRegion", "eu-west-1");
        String requestRegion = params.getOrDefault("requestRegion", "us-east-1");
        boolean evidenceAttached = Boolean.parseBoolean(params.getOrDefault("evidenceAttached", "false"));

        // Good practice: enforce region + evidence constraints.
        // Bad practice: ignore compliance controls and continue.
        boolean compliant = (dataRegion.equals(requestRegion) && evidenceAttached);

        result.put("scenario", "compliance-control");
        result.put("dataRegion", dataRegion);
        result.put("requestRegion", requestRegion);
        result.put("evidenceAttached", evidenceAttached);
        result.put("controlDecision", compliant ? "compliant" : "violation");
        result.put("expectedBehavior", "Production security controls natively enforced.");
        result.put("riskScore", compliant ? 27 : 86);
    }

    private void applyThreatScenario(Map<String, Object> result, Map<String, String> params) {
        int exploitability = parseInt(params.getOrDefault("exploitability", "80"), 80);
        int controlCoverage = parseInt(params.getOrDefault("controlCoverage", "85"), 85);

        // Good practice: improve control coverage to reduce residual risk.
        // Bad practice: low coverage leaves known attack paths exposed.
        int residualRisk = Math.max(0, exploitability - controlCoverage);

        result.put("scenario", "threat-mitigation");
        result.put("exploitability", exploitability);
        result.put("controlCoverage", controlCoverage);
        result.put("residualRisk", residualRisk);
        result.put("controlDecision", residualRisk <= 20 ? "mitigated" : "exposed");
        result.put("expectedBehavior", "Production security controls natively enforced.");
        result.put("riskScore", residualRisk <= 20 ? 29 : 89);
    }

    private void applyDefaultScenario(Map<String, Object> result) {
        result.put("scenario", "secure-by-default");
        result.put("controlDecision", "enforced");
        result.put("expectedBehavior", "Production security controls natively enforced.");
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
