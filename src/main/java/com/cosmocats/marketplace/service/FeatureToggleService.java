package com.cosmocats.marketplace.service;

import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

@Component
public class FeatureToggleService {

    private final Environment env;

    public FeatureToggleService(Environment env) {
        this.env = env;
    }

    public boolean isEnabled(String featureName) {
        return Boolean.parseBoolean(env.getProperty("feature." + featureName + ".enabled", "false"));
    }
}