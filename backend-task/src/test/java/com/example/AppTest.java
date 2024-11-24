package com.example;

import com.example.backendtask.controller.QuotaController;
import com.example.backendtask.service.QuotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuotaService quotaService;

    @InjectMocks
    private QuotaController quotaController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(quotaController).build();
    }

    @Test
    public void testGetResourceOneStatus_Allowed() throws Exception {
        // Given: A user is within the allowed quota (not blocked)
        Long userId = 1L;
        when(quotaService.checkQuotaResourceOneStatus(userId)).thenReturn(true);

        // When: The GET request is made for Resource One status
        mockMvc.perform(get("/api/quota/resource-one/{userId}", userId))
                // Then: The response should be OK (200) with a true body indicating the user is allowed
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        // Verify that the service method was called with the correct userId
        verify(quotaService, times(1)).checkQuotaResourceOneStatus(userId);
    }

    @Test
    public void testGetResourceOneStatus_Blocked() throws Exception {
        // Given: A user has exceeded the quota (blocked)
        Long userId = 1L;
        when(quotaService.checkQuotaResourceOneStatus(userId)).thenReturn(false);

        // When: The GET request is made for Resource One status
        mockMvc.perform(get("/api/quota/resource-one/{userId}", userId))
                // Then: The response should be OK (200) with a false body indicating the user is blocked
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        // Verify that the service method was called with the correct userId
        verify(quotaService, times(1)).checkQuotaResourceOneStatus(userId);
    }

    @Test
    public void testGetResourceTwoStatus_Allowed() throws Exception {
        // Given: A user is within the allowed quota for Resource Two (not blocked)
        Long userId = 1L;
        when(quotaService.checkQuotaResourceTwoStatus(userId)).thenReturn(true);

        // When: The GET request is made for Resource Two status
        mockMvc.perform(get("/api/quota/resource-two/{userId}", userId))
                // Then: The response should be OK (200) with a true body indicating the user is allowed
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        // Verify that the service method was called with the correct userId
        verify(quotaService, times(1)).checkQuotaResourceTwoStatus(userId);
    }

    @Test
    public void testGetResourceTwoStatus_Blocked() throws Exception {
        // Given: A user has exceeded the quota for Resource Two (blocked)
        Long userId = 1L;
        when(quotaService.checkQuotaResourceTwoStatus(userId)).thenReturn(false);

        // When: The GET request is made for Resource Two status
        mockMvc.perform(get("/api/quota/resource-two/{userId}", userId))
                // Then: The response should be OK (200) with a false body indicating the user is blocked
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        // Verify that the service method was called with the correct userId
        verify(quotaService, times(1)).checkQuotaResourceTwoStatus(userId);
    }
}
