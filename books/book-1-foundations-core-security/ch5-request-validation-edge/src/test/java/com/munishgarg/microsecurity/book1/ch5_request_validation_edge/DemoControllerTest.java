package com.munishgarg.microsecurity.book1.ch5_request_validation_edge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DemoController.class)
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void processPayment_WithValidPayload_ShouldReturnSuccess() throws Exception {
        String validJson = """
                {
                    "userId": "usr-123",
                    "userEmail": "alice@example.com",
                    "amount": 500,
                    "description": "Invoice payment"
                }
                """;

        mockMvc.perform(post("/api/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.processedAmount").value(500));
    }

    @Test
    void processPayment_WithMissingRequiredFields_ShouldReturnBadRequest() throws Exception {
        // Missing userId and email
        String invalidJson = """
                {
                    "amount": 500,
                    "description": "Invoice payment"
                }
                """;

        mockMvc.perform(post("/api/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.userId").value("User ID cannot be blank"))
                .andExpect(jsonPath("$.userEmail").value("Email cannot be blank"));
    }

    @Test
    void processPayment_WithNegativeAmount_ShouldReturnBadRequest() throws Exception {
        // Amount is less than @Min(1)
        String invalidJson = """
                {
                    "userId": "usr-123",
                    "userEmail": "alice@example.com",
                    "amount": -50,
                    "description": "Invoice payment"
                }
                """;

        mockMvc.perform(post("/api/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.amount").value("Amount must be at least 1"));
    }

    @Test
    void processPayment_WithMaliciousDescription_ShouldReturnBadRequest() throws Exception {
        // Description contains <script> which violates the @Pattern regex
        String invalidJson = """
                {
                    "userId": "usr-123",
                    "userEmail": "alice@example.com",
                    "amount": 500,
                    "description": "<script>alert('xss')</script>"
                }
                """;

        mockMvc.perform(post("/api/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value("Description contains invalid characters. Only alphanumeric characters and spaces are allowed."));
    }
}
