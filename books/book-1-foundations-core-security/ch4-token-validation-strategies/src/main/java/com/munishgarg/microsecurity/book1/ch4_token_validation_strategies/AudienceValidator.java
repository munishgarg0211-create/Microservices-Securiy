package com.munishgarg.microsecurity.book1.ch4_token_validation_strategies;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * A custom validator that inspects the 'aud' (audience) claim of the incoming JWT.
 * It prevents the "Confused Deputy" problem by ensuring tokens minted for one
 * Application/Service cannot be used to authenticate against another.
 */
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    private final String expectedAudience;
    
    private final OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is missing", null);

    public AudienceValidator(String expectedAudience) {
        this.expectedAudience = expectedAudience;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (jwt.getAudience().contains(expectedAudience)) {
            return OAuth2TokenValidatorResult.success();
        } else {
            return OAuth2TokenValidatorResult.failure(error);
        }
    }
}
