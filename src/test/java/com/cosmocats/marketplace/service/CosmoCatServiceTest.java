package com.cosmocats.marketplace.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CosmoCatServiceTest {

    @Autowired
    private CosmoCatService cosmoCatService;

    @MockBean
    private FeatureToggleService featureToggleService;

    @Test
    void getCosmoCats_shouldExecute_whenFeatureEnabled() {
        // Arrange: the feature ON
        when(featureToggleService.isEnabled("cosmoCats")).thenReturn(true);

        // Act
        List<String> result = cosmoCatService.getCosmoCats();

        // Assert
        assertNotNull(result);
        assertEquals("Space Garfield", result.getFirst());
    }

    @Test
    void getCosmoCats_shouldThrow_whenFeatureDisabled() {
        // Arrange: the feature OFF
        when(featureToggleService.isEnabled("cosmoCats")).thenReturn(false);

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cosmoCatService.getCosmoCats();
        });

        // Assert
        assertEquals("Feature 'cosmoCats' is disabled", exception.getMessage());
    }
}