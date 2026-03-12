package com.munishgarg.microsecurity.book1.ch4_federation_multitenancy;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A custom converter that inspects the incoming JWT for a custom 'tenant_id' claim.
 * It maps this claim into a standard Spring Security GrantedAuthority (e.g., ROLE_TENANT_ACME).
 * This allows endpoints to easily enforce tenant isolation using standard annotation checks:
 * @PreAuthorize("hasRole('TENANT_ACME')")
 */
@Component
public class TenantJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    // Helper that extracts standard scopes/roles from the JWT
    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // 1. Extract standard authorities (like SCOPE_read)
        Collection<GrantedAuthority> authorities = defaultGrantedAuthoritiesConverter.convert(jwt);

        // 2. Extract the custom "tenant_id" claim
        String tenantId = jwt.getClaimAsString("tenant_id");

        // 3. Map the tenant ID to a Spring Security Role (GrantedAuthority)
        if (tenantId != null && !tenantId.isBlank()) {
            GrantedAuthority tenantAuthority = new SimpleGrantedAuthority("ROLE_TENANT_" + tenantId.toUpperCase());
            
            // Combine standard scopes with our new tenant role
            authorities = Stream.concat(authorities.stream(), Stream.of(tenantAuthority))
                    .collect(Collectors.toSet());
        }

        // Return the fully contextualized token
        return new JwtAuthenticationToken(jwt, authorities);
    }
}
