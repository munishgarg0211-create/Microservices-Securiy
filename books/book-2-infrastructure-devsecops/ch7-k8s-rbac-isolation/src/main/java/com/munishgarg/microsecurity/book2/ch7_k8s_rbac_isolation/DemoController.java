package com.munishgarg.microsecurity.book2.ch7_k8s_rbac_isolation;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
// COPY-PASTE READY: Fully unified and secured API endpoint controller.
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
            @RequestParam(defaultValue = "secure") String mode,
            @RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
