package com.munishgarg.microsecurity.book1.ch1_security_shift_modeling;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch1-security-shift-modeling";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Demonstrate security 'shifting left' by integrating threat modeling and risk assessment early in the lifecycle.";
    private static final String CONCEPT = "Security Shift-Left Modeling";
    private static final String CONTROL_FAMILY = "THREAT";

    /**
     * Executes a security shift-left demonstration.
     * Evaluates the residual risk based on the maturity of early security activities.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        boolean designReviewDone = Boolean.parseBoolean(params.getOrDefault("designReview", "true"));
        boolean threatModelingDone = Boolean.parseBoolean(params.getOrDefault("threatModeling", "true"));
        int earlyMitigationRate = Integer.parseInt(params.getOrDefault("earlyMitigationRate", "85"));

        // Defense Logic: Shifting left requires active participation in design and modeling phases
        boolean isShiftLeftEffective = designReviewDone && threatModelingDone && (earlyMitigationRate >= 80);

        result.put("designReviewDone", designReviewDone);
        result.put("threatModelingDone", threatModelingDone);
        result.put("earlyMitigationRate", earlyMitigationRate + "%");
        result.put("controlDecision", isShiftLeftEffective ? "shielded" : "exposed");
        result.put("expectedBehavior", "Security must be integrated into the design phase (Shift Left) to identify and mitigate architectural flaws before a single line of code is written.");
        result.put("description", "Shift-left modeling focuses on early detection of vulnerabilities through systematic design reviews and threat modeling, significantly reducing late-stage remediation costs.");
        result.put("riskScore", isShiftLeftEffective ? 12 : 88);

        return result;
    }
}
