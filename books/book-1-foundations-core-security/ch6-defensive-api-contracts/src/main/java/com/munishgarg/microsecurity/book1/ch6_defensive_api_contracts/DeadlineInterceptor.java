package com.munishgarg.microsecurity.book1.ch6_defensive_api_contracts;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class DeadlineInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String deadlineHeader = request.getHeader("X-Deadline-Ms");
        if (deadlineHeader != null) {
            try {
                long millisFromNow = Long.parseLong(deadlineHeader);
                DeadlineContext.set(Instant.now().plusMillis(millisFromNow));
            } catch (NumberFormatException e) {
                // Ignore invalid header
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        DeadlineContext.clear();
    }
}
