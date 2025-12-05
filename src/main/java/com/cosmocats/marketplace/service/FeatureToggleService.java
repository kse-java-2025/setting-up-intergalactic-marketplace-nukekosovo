package com.cosmocats.marketplace.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeatureToggleService {

    private final boolean cosmoCatsEnabled;
    private final boolean kittyProductsEnabled;

    public FeatureToggleService(
            @Value("${feature.cosmoCats.enabled:false}") boolean cosmoCatsEnabled,
            @Value("${feature.kittyProducts.enabled:false}") boolean kittyProductsEnabled
    ) {
        this.cosmoCatsEnabled = cosmoCatsEnabled;
        this.kittyProductsEnabled = kittyProductsEnabled;
    }

    public boolean isEnabled(String featureName) {
        return switch (featureName) {
            case "cosmoCats" -> cosmoCatsEnabled;
            case "kittyProducts" -> kittyProductsEnabled;
            default -> false;
        };
    }
}