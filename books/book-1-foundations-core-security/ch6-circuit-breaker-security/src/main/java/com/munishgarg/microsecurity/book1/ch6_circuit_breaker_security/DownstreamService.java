package com.munishgarg.microsecurity.book1.ch6_circuit_breaker_security;

import org.springframework.stereotype.Service;

@Service
public class DownstreamService {

    /**
     * Simulates a downstream call that can fail or hang.
     */
    public String call(boolean shouldFail, long delayMs) {
        if (shouldFail) {
            throw new RuntimeException("Downstream Service Failure!");
        }
        
        if (delayMs > 0) {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        return "Success from Downstream";
    }
}
