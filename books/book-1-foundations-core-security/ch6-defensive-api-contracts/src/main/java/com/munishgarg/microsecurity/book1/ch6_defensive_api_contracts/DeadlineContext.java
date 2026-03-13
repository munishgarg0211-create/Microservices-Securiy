package com.munishgarg.microsecurity.book1.ch6_defensive_api_contracts;

import java.time.Instant;

public class DeadlineContext {
    private static final ThreadLocal<Instant> DEADLINE = new ThreadLocal<>();

    public static void set(Instant deadline) {
        DEADLINE.set(deadline);
    }

    public static Instant get() {
        return DEADLINE.get();
    }

    public static boolean isExpired() {
        Instant deadline = DEADLINE.get();
        return deadline != null && Instant.now().isAfter(deadline);
    }

    public static void clear() {
        DEADLINE.remove();
    }
}
