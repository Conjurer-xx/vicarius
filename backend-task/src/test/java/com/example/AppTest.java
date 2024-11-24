package com.example;

import com.example.backendtask.controller.UserController;
import com.example.backendtask.model.User;
import com.example.backendtask.service.QuotaService;
import com.example.backendtask.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AppTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private QuotaService quotaService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateUser() throws Exception {
        User user = new User(1L, "John", "Doe");
        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testGetUserQuotaResourceOneStatus() throws Exception {
        Long userId = 1L;
        when(quotaService.getQuotaResourceOneStatus(userId)).thenReturn(true);

        mockMvc.perform(get("/users/{userId}/quota-resource-one-status", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        // Verify that QuotaService was called
        verify(quotaService, times(1)).getQuotaResourceOneStatus(userId);
    }

    @Test
    void testGetUserQuotaResourceTwoStatus() throws Exception {
        Long userId = 1L;
        when(quotaService.getQuotaResourceTwoStatus(userId)).thenReturn(false);

        mockMvc.perform(get("/users/{userId}/quota-resource-two-status", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));

        // Verify that QuotaService was called
        verify(quotaService, times(1)).getQuotaResourceTwoStatus(userId);
    }

    @Test
    void testConsumeQuotaResourceOne() throws Exception {
        Long userId = 1L;
        doNothing().when(quotaService).consumeQuotaResourceOne(userId);

        mockMvc.perform(post("/users/{userId}/consume-resource-one", userId))
                .andExpect(status().isOk());

        // Verify that QuotaService was called
        verify(quotaService, times(1)).consumeQuotaResourceOne(userId);
    }

    @Test
    void testConsumeQuotaResourceTwo() throws Exception {
        Long userId = 1L;
        doNothing().when(quotaService).consumeQuotaResourceTwo(userId);

        mockMvc.perform(post("/users/{userId}/consume-resource-two", userId))
                .andExpect(status().isOk());

        // Verify that QuotaService was called
        verify(quotaService, times(1)).consumeQuotaResourceTwo(userId);
    }

    @Test
    void testConsumeQuotaResourceOneWhenBlocked() throws Exception {
        Long userId = 1L;
        doThrow(new RuntimeException("User is permanently blocked from consuming resources."))
                .when(quotaService).consumeQuotaResourceOne(userId);

        mockMvc.perform(post("/users/{userId}/consume-resource-one", userId))
                .andExpect(status().isForbidden())
                .andExpect(content().string("User is permanently blocked from consuming resources."));

        // Verify that QuotaService was called
        verify(quotaService, times(1)).consumeQuotaResourceOne(userId);
    }

    @Test
    void testConsumeQuotaResourceTwoWhenBlocked() throws Exception {
        Long userId = 1L;
        doThrow(new RuntimeException("User is permanently blocked from consuming resources."))
                .when(quotaService).consumeQuotaResourceTwo(userId);

        mockMvc.perform(post("/users/{userId}/consume-resource-two", userId))
                .andExpect(status().isForbidden())
                .andExpect(content().string("User is permanently blocked from consuming resources."));

        // Verify that QuotaService was called
        verify(quotaService, times(1)).consumeQuotaResourceTwo(userId);
    }

    @Test
    void testCreateUserInvalidData() throws Exception {
        User user = new User();
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }
}
