package com.munishgarg.microsecurity.book1.ch4_identity_propagation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class DemoServiceTest {

    private DemoService service;
    private WebClient webClientMock;
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
    private WebClient.ResponseSpec responseSpecMock;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        webClientMock = mock(WebClient.class);
        requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(org.mockito.ArgumentMatchers.anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);

        service = new DemoService(webClientMock);
    }

    @Test
    void fetchDownstreamProfile_ShouldEnrichResponse() {
        Map<String, String> mockDownstreamResponse = new LinkedHashMap<>();
        mockDownstreamResponse.put("status", "success");
        mockDownstreamResponse.put("authenticatedUserAtDownstream", "test-user");

        when(responseSpecMock.bodyToMono(any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(mockDownstreamResponse));

        Mono<Map<String, String>> resultMono = service.fetchDownstreamProfile();

        StepVerifier.create(resultMono)
                .assertNext(result -> {
                    assertEquals("success", result.get("status"));
                    assertEquals("test-user", result.get("authenticatedUserAtDownstream"));
                    assertEquals("Frontend-API -> Downstream-API", result.get("serviceChain"));
                    assertNotNull(result.get("securityMechanism"));
                })
                .verifyComplete();
    }

    @Test
    void getMockedDownstreamData_ShouldExtractPrincipalFromSecurityContext() {
        // Arrange active security context
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("downstream-user", "N/A", null)
        );

        // Act
        Map<String, String> result = service.getMockedDownstreamData();

        // Assert
        assertEquals("success", result.get("status"));
        assertEquals("Sensitive profile details", result.get("downstreamData"));
        assertEquals("downstream-user", result.get("authenticatedUserAtDownstream"));
        
        // Clean up
        SecurityContextHolder.clearContext();
    }
}
