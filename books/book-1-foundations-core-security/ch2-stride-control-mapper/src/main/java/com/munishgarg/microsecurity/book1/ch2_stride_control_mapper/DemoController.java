package com.munishgarg.microsecurity.book1.ch2_stride_control_mapper;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demonstrates the mapping of STRIDE threats to security controls.
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    /**
     * Executes the STRIDE mapping demo.
     * Use 'threat' query parameter to see the mapped control.
     * Options: Spoofing, Tampering, Repudiation, Information Disclosure, Denial of Service, Elevation of Privilege.
     * Example: ?threat=Spoofing
     */
    @GetMapping
    public Map<String, Object> getDemo(@RequestParam Map<String, String> params) {
        return demoService.demo(params);
    }
}
