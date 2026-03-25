# ch15 - Recipe: Istio Service Mesh Policies

- Book: book-3-advanced-patterns
- Chapter: ch15
- Status: executable-demo
- Suggested stack: Istio

## Objective
mTLS and authz policy manifests.

## Mitigation Logic
- Control family: `POLICY` (deployment/runtime policy gate enforcement).
- Core robust security decision model in code:
- Enforced security baseline (production copy/paste pattern):
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
2. Run secure path: `GET /api/demo?imageSigned=false&hasSbom=false&criticalVulns=2`.
3. Run baseline path with same inputs and compare policy-gate impact.

## Acceptance Criteria
- The reference pattern implements a deterministic, control-enforced decision for the chapter scenario.
- API response includes measurable fields (`controlDecision`, `expectedBehavior`, `riskScore`) and tests validate them.

## Generated Demo Sample
- Runtime: Spring Boot 3.x, Java 21
- API: GET /api/demo (supports `mode` + scenario params)
- Tests: DemoServiceTest, DemoControllerTest

## Code Demonstration Map

## Quick Start
- Run: mvn spring-boot:run
- Test: mvn test
