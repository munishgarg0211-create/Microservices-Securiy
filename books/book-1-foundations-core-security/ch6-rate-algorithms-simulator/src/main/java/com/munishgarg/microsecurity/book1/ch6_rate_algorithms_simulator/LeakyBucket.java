package com.munishgarg.microsecurity.book1.ch6_rate_algorithms_simulator;

/**
 * Leaky Bucket: Smooths out traffic. Requests "leak" out at a constant rate.
 */
public class LeakyBucket {
    private final long capacity;
    private final double leakRate; // requests per ms
    private long currentWaterLevel;
    private long lastLeakTimestamp;

    public LeakyBucket(long capacity, double leakRatePerSecond) {
        this.capacity = capacity;
        this.leakRate = leakRatePerSecond / 1000.0;
        this.currentWaterLevel = 0;
        this.lastLeakTimestamp = System.currentTimeMillis();
    }

    public synchronized boolean tryConsume() {
        leak();
        if (currentWaterLevel < capacity) {
            currentWaterLevel++;
            return true;
        }
        return false;
    }

    private void leak() {
        long now = System.currentTimeMillis();
        long delta = now - lastLeakTimestamp;
        long leaked = (long) (delta * leakRate);

        if (leaked > 0) {
            currentWaterLevel = Math.max(0, currentWaterLevel - leaked);
            lastLeakTimestamp = now;
        }
    }

    public long getCurrentLoad() {
        leak();
        return currentWaterLevel;
    }
}
