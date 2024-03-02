package com.tests.app;


import com.tests.app.model.Quota;
import com.tests.app.model.User;
import com.tests.app.repository.elasticsearch.ElasticQuotaRepository;
import com.tests.app.repository.elasticsearch.ElasticUserRepository;
import com.tests.app.repository.jpa.QuotaRepository;
import com.tests.app.repository.jpa.UserRepository;
import com.tests.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ElasticUserRepository elasticUserRepository;

    @Mock
    private QuotaRepository quotaRepository;

    @Mock
    private ElasticQuotaRepository elasticQuotaRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setUserId("testUser");

        when(userRepository.findByUserId("testUser")).thenReturn(user);
        when(elasticUserRepository.findByUserId("testUser")).thenReturn(user);

        userService.updateUser("testUser", user);

        verify(userRepository, times(1)).save(user);
        verify(elasticUserRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUserId("testUser");

        when(userRepository.findByUserId("testUser")).thenReturn(user);
        when(elasticUserRepository.findByUserId("testUser")).thenReturn(user);

        userService.deleteUser("testUser");

        verify(userRepository, times(1)).delete(user);
        verify(elasticUserRepository, times(1)).delete(user);
    }

    @Test
    void testConsumeQuota() {
        Quota quota = new Quota();
        quota.setUserId("testUser");
        quota.setRequestCount(0);

        when(quotaRepository.findByUserId("testUser")).thenReturn(quota);
        when(elasticQuotaRepository.findByUserId("testUser")).thenReturn(quota);

        userService.consumeQuota("testUser");

        verify(quotaRepository, times(1)).findByUserId("testUser");
        verify(elasticQuotaRepository, times(1)).findByUserId("testUser");

    }

    @Test
    void testGetUser() {
        User user = new User();
        user.setUserId("testUser");
        Quota quota = new Quota();
        quota.setUserId("testUser");
        quota.setRequestCount(1);

        when(userRepository.findByUserId("testUser")).thenReturn(user);
        when(elasticUserRepository.findByUserId("testUser")).thenReturn(user);
        when(quotaRepository.findByUserId("testUser")).thenReturn(quota);
        when(elasticQuotaRepository.findByUserId("testUser")).thenReturn(quota);

        User result = userService.getUser("testUser");

        assertNotNull(result);
    }





}

