#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")" && pwd)"

mkdir -p "$ROOT/docs" "$ROOT/books"

cat > "$ROOT/README.md" <<'MD'
# Microservices Security Demo Projects

This repository contains chapter-wise demo project scaffolds for the 3-book series:

- Book 1: Foundations & Core Security
- Book 2: Infrastructure & DevSecOps
- Book 3: Advanced Patterns

Each concept has its own project folder with:

- `README.md` (what to build and how to run)
- `TASKS.md` (implementation checklist)
- `src/` and `infra/` starter folders
MD

cat > "$ROOT/docs/IMPLEMENTATION_TRACKER.md" <<'MD'
# Implementation Tracker

Status legend:
- `scaffolded`: folder/docs created
- `in_progress`: implementation started
- `complete`: demo runnable end-to-end

Update each project status in its local `README.md` as work progresses.
MD

make_project() {
  local book_dir="$1"
  local chapter="$2"
  local slug="$3"
  local title="$4"
  local objective="$5"
  local stack="$6"

  local dir="$ROOT/books/$book_dir/${chapter}-${slug}"
  mkdir -p "$dir/src" "$dir/infra"

  cat > "$dir/README.md" <<MD
# ${chapter} - ${title}

- Book: ${book_dir}
- Chapter: ${chapter}
- Status: scaffolded
- Suggested stack: ${stack}

## Objective
${objective}

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
MD

  cat > "$dir/TASKS.md" <<'MD'
# Tasks

- [ ] Create minimal service or policy assets
- [ ] Add insecure baseline scenario
- [ ] Add secured implementation
- [ ] Add tests/validation scripts
- [ ] Add run steps and expected output
- [ ] Add cleanup/reset steps
MD

  touch "$dir/src/.gitkeep" "$dir/infra/.gitkeep"
}

# Book 1
make_project "book-1-foundations-core-security" "ch1" "security-shift-modeling" "Monolith to Microservices Security Shift" "Threat model comparing monolith vs distributed architecture." "Spring Boot, Mermaid, STRIDE"
make_project "book-1-foundations-core-security" "ch1" "attack-surface-mapper" "Expanded Threat Surface Mapper" "Generate attack-surface map from APIs and service topology." "OpenAPI, Graphviz"
make_project "book-1-foundations-core-security" "ch1" "principles-scorecard" "Security Principles Scorecard" "Enforce least privilege and secure defaults checks." "OPA, Conftest"
make_project "book-1-foundations-core-security" "ch1" "sdlc-security-pipeline" "Lifecycle Security Pipeline" "Integrate security checks from build to runtime." "GitHub Actions, Trivy, Semgrep"
make_project "book-1-foundations-core-security" "ch1" "role-based-learning-labs" "Role-Based Lab Navigation" "Map content to dev/architect/secops paths." "Markdown, MkDocs"

make_project "book-1-foundations-core-security" "ch2" "owasp-bola-lab" "OWASP in Microservices" "Demonstrate BOLA and mitigation in service endpoints." "Spring Boot, JUnit"
make_project "book-1-foundations-core-security" "ch2" "ssrf-impersonation-lab" "Insider Threat & SSRF" "Show SSRF path and hardened outbound controls." "Flask/Spring, NetworkPolicy"
make_project "book-1-foundations-core-security" "ch2" "misconfig-api-abuse-lab" "Misconfiguration & API Abuse" "Expose misconfigured endpoints and fix them." "K8s manifests, Spring Actuator"
make_project "book-1-foundations-core-security" "ch2" "supply-chain-mini-sim" "Breach Replay Mini Simulation" "Safe simulation of dependency/pipeline compromise controls." "Mock CI, signatures"
make_project "book-1-foundations-core-security" "ch2" "stride-control-mapper" "Threat to Control Mapping" "Map STRIDE threats to defense controls." "Markdown, templates"

make_project "book-1-foundations-core-security" "ch3" "mtls-baseline" "Transport Security Essentials" "Establish mTLS between two services." "Spring Boot, cert-manager"
make_project "book-1-foundations-core-security" "ch3" "https-hardening" "Secure HTTP with TLS" "TLS cert rotation and HTTPS-only behavior." "NGINX, Spring"
make_project "book-1-foundations-core-security" "ch3" "grpc-tls-authz" "gRPC Security" "Secure gRPC with TLS and auth interceptors." "gRPC Java"
make_project "book-1-foundations-core-security" "ch3" "kafka-security-baseline" "Kafka Message Security" "SASL/SCRAM + ACL + TLS producer/consumer." "Kafka, Docker Compose"
make_project "book-1-foundations-core-security" "ch3" "protocol-threat-comparison" "Protocol-Aware Threat Models" "Compare HTTP/gRPC/Kafka threat patterns." "Docs + sample payloads"

make_project "book-1-foundations-core-security" "ch4" "identity-propagation" "Identity in Distributed Calls" "Propagate user identity across services securely." "Spring Security"
make_project "book-1-foundations-core-security" "ch4" "oauth2-grants-demo" "OAuth2 Grant Types" "Compare client credentials and auth code flows." "Keycloak, Spring"
make_project "book-1-foundations-core-security" "ch4" "jwt-validation-rotation" "JWT Validation and Rotation" "Validate JWT and rotate signing keys safely." "JWK, Spring"
make_project "book-1-foundations-core-security" "ch4" "rbac-abac-demo" "RBAC and ABAC" "Implement role and attribute-based access checks." "Spring Authorization"
make_project "book-1-foundations-core-security" "ch4" "auth-anti-patterns-lab" "Auth Anti-Patterns" "Demonstrate common token/auth pitfalls." "Spring"
make_project "book-1-foundations-core-security" "ch4" "token-validation-strategies" "Distributed Token Validation" "Compare introspection vs local validation." "Redis, Spring"
make_project "book-1-foundations-core-security" "ch4" "federation-multitenancy" "Identity Federation and Multi-Tenancy" "Tenant-scoped claims and policy checks." "OIDC, Keycloak"
make_project "book-1-foundations-core-security" "ch4" "spring-auth-starter" "Implementation Recipes Starter" "Reusable Spring auth starter module." "Spring Boot starter"

make_project "book-1-foundations-core-security" "ch5" "gateway-role-demo" "Gateway as Security Control" "Show direct-service vs gateway-controlled paths." "Spring Cloud Gateway"
make_project "book-1-foundations-core-security" "ch5" "perimeter-threat-mitigation" "Perimeter Threat Prevention" "Bot and credential stuffing mitigation pipeline." "Gateway filters"
make_project "book-1-foundations-core-security" "ch5" "request-validation-edge" "Request Validation at Edge" "OpenAPI schema and header validation at gateway." "Gateway + JSON Schema"
make_project "book-1-foundations-core-security" "ch5" "idp-integration-patterns" "IdP Integration Patterns" "Token relay and phantom token patterns." "Keycloak, Gateway"
make_project "book-1-foundations-core-security" "ch5" "geo-ip-rate-controls" "Rate, Geo-Fencing, IP Controls" "Context-aware edge controls and policy decisions." "Redis, GeoIP DB"
make_project "book-1-foundations-core-security" "ch5" "edge-observability" "Edge Audit and Observability" "403 dashboards and correlation IDs at edge." "Prometheus, Grafana"

make_project "book-1-foundations-core-security" "ch6" "defensive-api-contracts" "Defensive Service Design" "Build abuse-resistant API contract patterns." "Spring"
make_project "book-1-foundations-core-security" "ch6" "rate-algorithms-simulator" "Quota Algorithm Simulator" "Token bucket vs leaky bucket simulation." "Node/Python"
make_project "book-1-foundations-core-security" "ch6" "gateway-throttling-redis" "Gateway Throttling" "Distributed throttling with Redis." "Spring Cloud Gateway"
make_project "book-1-foundations-core-security" "ch6" "circuit-breaker-security" "Circuit Breaking for Fault Isolation" "Secure fallback modes with resilience patterns." "Resilience4j"
make_project "book-1-foundations-core-security" "ch6" "dynamic-policy-engine" "Dynamic Policy Configuration" "Externalized runtime security policy updates." "OPA, Config Server"

# Book 2
make_project "book-2-infrastructure-devsecops" "ch7" "image-hygiene-signing" "Docker Image Hygiene and Signing" "Image scanning and signature verification gates." "Trivy, Cosign"
make_project "book-2-infrastructure-devsecops" "ch7" "k8s-rbac-isolation" "Kubernetes RBAC and Namespace Isolation" "Least-privilege RBAC lab with namespace boundaries." "Kubernetes"
make_project "book-2-infrastructure-devsecops" "ch7" "secrets-pod-security" "Secrets and Pod Security" "Vault-backed secrets with pod security controls." "Vault, PSA"
make_project "book-2-infrastructure-devsecops" "ch7" "network-policy-microseg" "Network Policy Microsegmentation" "Deny-by-default pod network policy demo." "Calico/Cilium"
make_project "book-2-infrastructure-devsecops" "ch7" "audit-runtime-protection" "Auditability and Runtime Protections" "Falco runtime detection with audit trails." "Falco, Audit logs"

make_project "book-2-infrastructure-devsecops" "ch8" "mesh-concepts-demo" "Service Mesh Concepts" "Data/control plane demo with sidecars." "Istio"
make_project "book-2-infrastructure-devsecops" "ch8" "istio-mtls-identity" "mTLS and Identity Injection" "STRICT mTLS with workload identity." "Istio, SPIFFE"
make_project "book-2-infrastructure-devsecops" "ch8" "istio-authz-traffic" "Authorization Policies" "Service-to-service authZ policies in mesh." "Istio"
make_project "book-2-infrastructure-devsecops" "ch8" "egress-control-routing" "Egress Control and Routing" "Restrict egress and permit allowlisted destinations." "Istio Egress"
make_project "book-2-infrastructure-devsecops" "ch8" "mesh-observability-enforcement" "Mesh Observability" "Trace and verify policy enforcement events." "Kiali, Jaeger"

make_project "book-2-infrastructure-devsecops" "ch9" "devsecops-template" "DevSecOps Principles" "Secure-by-default repository and workflow template." "GitHub Actions"
make_project "book-2-infrastructure-devsecops" "ch9" "agile-threat-model-lite" "Threat Modeling in Agile" "PR-integrated lightweight threat model process." "Templates"
make_project "book-2-infrastructure-devsecops" "ch9" "sast-dast-pipeline" "Static and Dynamic Analysis" "SAST + DAST integrated CI stages." "Semgrep, ZAP"
make_project "book-2-infrastructure-devsecops" "ch9" "secrets-sbom-supplychain" "Secrets and Supply Chain Validation" "Secret scanning and SBOM verification gates." "Gitleaks, Syft"
make_project "book-2-infrastructure-devsecops" "ch9" "policy-as-code-cicd" "Policy as Code" "OPA/Kyverno checks in pipeline." "OPA, Kyverno"

make_project "book-2-infrastructure-devsecops" "ch10" "security-monitoring-slos" "Role of Monitoring in Security" "Define and monitor security-centric SLOs." "Prometheus"
make_project "book-2-infrastructure-devsecops" "ch10" "centralized-enriched-logs" "Centralized Logging" "Structured logs with user/trace enrichment." "Fluent Bit, Loki"
make_project "book-2-infrastructure-devsecops" "ch10" "metrics-traces-correlation" "Correlation Analysis" "Link metrics, traces, and logs for incidents." "OpenTelemetry"
make_project "book-2-infrastructure-devsecops" "ch10" "anomaly-detection-lab" "Behavioral Anomaly Detection" "Baseline behavior model and anomaly alerts." "Python, Prometheus"
make_project "book-2-infrastructure-devsecops" "ch10" "alerting-response-playbooks" "Alerting and Incident Response" "Alert routing and playbook automation." "Alertmanager"

make_project "book-2-infrastructure-devsecops" "bonus" "prod-deployment-gates" "Production Deployment Checklist" "Automated deployment security gate checks." "Shell, CI"
make_project "book-2-infrastructure-devsecops" "bonus" "incident-response-runbook" "Incident Response Playbook" "Tabletop drill and incident SOP assets." "Markdown, scripts"
make_project "book-2-infrastructure-devsecops" "bonus" "container-hardening-lab" "Container Hardening Checklist" "Distroless hardening and runtime restrictions." "Docker"
make_project "book-2-infrastructure-devsecops" "bonus" "observability-stack-guide" "Observability Setup Guide" "Deploy baseline observability for security." "Prometheus stack"

# Book 3
make_project "book-3-advanced-patterns" "ch11" "log4shell-breach-replay" "Case Study: Log4Shell" "Safe breach replay and defense controls." "Java lab"
make_project "book-3-advanced-patterns" "ch11" "equifax-timeline-lab" "Case Study: Equifax Timeline" "Patch-lag and exploit timeline simulation." "Docs + scripts"
make_project "book-3-advanced-patterns" "ch11" "capitalone-ssrf-lab" "Case Study: Capital One SSRF" "SSRF chain and IAM hardening demo." "Flask/AWS mock"
make_project "book-3-advanced-patterns" "ch11" "codecov-supplychain-lab" "Case Study: Codecov" "Pipeline trust hardening and signature verification." "CI workflows"
make_project "book-3-advanced-patterns" "ch11" "attack-framework-mapping" "Preventive Framework Mapping" "Map ATT&CK/Kill Chain to controls." "Templates"

make_project "book-3-advanced-patterns" "ch12" "security-first-adr" "Security as Design Concern" "Security-focused architecture decision records." "ADR templates"
make_project "book-3-advanced-patterns" "ch12" "zero-trust-reference" "Zero Trust in Microservices" "Workload identity and trust policy baseline." "SPIFFE/SPIRE"
make_project "book-3-advanced-patterns" "ch12" "privilege-segregation-demo" "Privilege Segregation" "Split-plane privilege boundaries across layers." "Spring + IAM"
make_project "book-3-advanced-patterns" "ch12" "secure-style-comparison" "Secure Architecture Styles" "Compare hexagonal and event-driven security." "Java + Kafka"
make_project "book-3-advanced-patterns" "ch12" "secure-failures-lab" "Fault Tolerance and Secure Failures" "Fail-closed patterns with resilience." "Resilience4j"

make_project "book-3-advanced-patterns" "ch13" "regulation-control-matrix" "Regulatory Requirements Mapping" "Map GDPR/HIPAA/PCI controls to architecture." "Docs"
make_project "book-3-advanced-patterns" "ch13" "immutable-audit-log" "Audit Trails Best Practices" "Tamper-evident audit log implementation." "Kafka/WORM"
make_project "book-3-advanced-patterns" "ch13" "data-residency-router" "Data Residency and Privacy" "Geo-sharding router with privacy controls." "Spring, DB"
make_project "book-3-advanced-patterns" "ch13" "compliance-as-code-pack" "Compliance as Code" "OPA/Conftest policy pack for compliance." "OPA"
make_project "book-3-advanced-patterns" "ch13" "governance-operating-model" "Governance Models" "Security champion model artifacts and KPIs." "Docs"

make_project "book-3-advanced-patterns" "ch14" "ai-security-triage" "AI-Driven Security Tooling" "AI-assisted anomaly triage with human approval." "Python"
make_project "book-3-advanced-patterns" "ch14" "federated-identity-multicloud" "Federated Identity" "Cross-cloud identity federation demo." "OIDC/SAML"
make_project "book-3-advanced-patterns" "ch14" "crypto-agility-pqc" "Quantum-Resistant Preparedness" "Crypto-agility abstraction and migration path." "Java crypto"
make_project "book-3-advanced-patterns" "ch14" "ethical-risk-checklist" "Ethical and Societal Controls" "Privacy and ethics decision checklist." "Templates"
make_project "book-3-advanced-patterns" "ch14" "agentic-self-healing" "Agentic Self-Healing Security" "Automated policy rollback with safeguards." "ArgoCD/OPA"

make_project "book-3-advanced-patterns" "ch15" "jwt-filter-role-checks" "Recipe: JWT Filter and Role Checks" "Implement JWT parsing, role extraction, and guards." "Spring Security"
make_project "book-3-advanced-patterns" "ch15" "oauth2-bff-flow" "Recipe: OAuth2 BFF Flow" "Gateway BFF auth flow with session cookie." "Spring Cloud Gateway"
make_project "book-3-advanced-patterns" "ch15" "secure-rest-patterns" "Recipe: Secure REST API Patterns" "Headers, cache controls, and API hardening." "Spring Boot"
make_project "book-3-advanced-patterns" "ch15" "gateway-rate-limiting" "Recipe: Gateway Rate Limiting" "Per-user token-aware rate limiting." "Gateway + Redis"
make_project "book-3-advanced-patterns" "ch15" "spring-vault-secrets" "Recipe: Secret Injection with Vault" "Runtime secret injection from Vault." "Spring Vault"
make_project "book-3-advanced-patterns" "ch15" "grpc-security-recipe" "Recipe: gRPC Security" "mTLS and authorization interceptors for gRPC." "gRPC"
make_project "book-3-advanced-patterns" "ch15" "kafka-security-recipe" "Recipe: Kafka Security" "SASL/SCRAM + SSL recipe project." "Kafka"
make_project "book-3-advanced-patterns" "ch15" "k8s-security-contexts" "Recipe: Kubernetes Security Contexts" "RunAsNonRoot and restricted context examples." "Kubernetes"
make_project "book-3-advanced-patterns" "ch15" "istio-policy-recipe" "Recipe: Istio Service Mesh Policies" "mTLS and authz policy manifests." "Istio"
make_project "book-3-advanced-patterns" "ch15" "cicd-security-recipe" "Recipe: CI/CD Security Integration" "Pipeline templates with security gates." "GitHub Actions"

# Book READMEs
for book in book-1-foundations-core-security book-2-infrastructure-devsecops book-3-advanced-patterns; do
  out="$ROOT/books/$book/README.md"
  mkdir -p "$ROOT/books/$book"
  {
    echo "# ${book}"
    echo
    echo "Concept projects for this book are organized by chapter prefix (e.g., \
\`ch4-...\`)."
    echo
    echo "Use: \
\`find . -maxdepth 1 -type d | sort\`"
  } > "$out"
done
