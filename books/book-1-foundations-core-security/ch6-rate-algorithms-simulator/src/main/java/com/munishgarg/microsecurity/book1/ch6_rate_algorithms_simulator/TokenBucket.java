package com.munishgarg.microsecurity.book1.ch6_rate_algorithms_simulator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Token Bucket: Allows bursts up to 'capacity', refills tokens over time.
 */
public class TokenBucket {
    private final long capacity;
    private final double refillRate; // tokens per ms
    private final AtomicLong tokens;
    private long lastRefillTimestamp;

    public TokenBucket(long capacity, double refillRatePerSecond) {
        this.capacity = capacity;
        this.refillRate = refillRatePerSecond / 1000.0;
        this.tokens = new AtomicLong(capacity);
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    public synchronized boolean tryConsume() {
        refill();
        if (tokens.get() >= 1) {
            tokens.decrementAndGet();
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long delta = now - lastRefillTimestamp;
        long newTokens = (long) (delta * refillRate);
        
        if (newTokens > 0) {
            tokens.set(Math.min(capacity, tokens.get() + newTokens));
            lastRefillTimestamp = now;
        }
    }

    public long getAvailableTokens() {
        refill();
        return tokens.get();
    }
}
