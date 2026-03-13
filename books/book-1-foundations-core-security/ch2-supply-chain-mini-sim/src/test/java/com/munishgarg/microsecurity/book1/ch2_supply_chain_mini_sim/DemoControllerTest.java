package com.munishgarg.microsecurity.book1.ch2_supply_chain_mini_sim;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAllowSignedWithSbom() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("imageSigned", "true")
                .param("hasSbom", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("allow"))
                .andExpect(jsonPath("$.riskScore").value(18));
    }

    @Test
    void shouldDenyUnsignedImage() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("imageSigned", "false")
                .param("hasSbom", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("deny"))
                .andExpect(jsonPath("$.riskScore").value(94));
    }

    @Test
    void shouldDenyMissingSbom() throws Exception {
        mockMvc.perform(get("/api/demo")
                .param("imageSigned", "true")
                .param("hasSbom", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.controlDecision").value("deny"));
    }

    @Test
    void shouldReturnStandardMetadata() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("ch2-supply-chain-mini-sim"))
                .andExpect(jsonPath("$.concept").value("Supply Chain Security"))
                .andExpect(jsonPath("$.controlFamily").value("POLICY"));
    }
}
