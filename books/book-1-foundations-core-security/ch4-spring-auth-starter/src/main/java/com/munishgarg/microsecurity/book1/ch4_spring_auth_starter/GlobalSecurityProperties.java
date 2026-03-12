package com.munishgarg.microsecurity.book1.ch4_spring_auth_starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Demonstrates a declarative security policy configuration class.
 * Centralizing this in a "Starter" allows Platform Security teams to push
 * global defaults across hundreds of microservices.
 */
@Configuration
@ConfigurationProperties(prefix = "corp.security.global")
public class GlobalSecurityProperties {

    /**
     * Toggles whether the strict platform-wide Security Config is enabled.
     * Default is true to enforce Secure-by-Default principles.
     */
    private boolean enabled = true;

    /**
     * Represents the trusted Identity Provider URL that all microservices
     * must validate incoming JWTs against.
     */
    private String issuerUri = "https://example.com/oauth2/default";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getIssuerUri() {
        return issuerUri;
    }

    public void setIssuerUri(String issuerUri) {
        this.issuerUri = issuerUri;
    }
}
