package com.munishgarg.microsecurity.book1.ch3_mtls_baseline;

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
