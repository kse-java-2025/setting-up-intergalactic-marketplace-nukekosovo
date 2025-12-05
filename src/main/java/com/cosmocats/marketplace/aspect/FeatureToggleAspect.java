package com.cosmocats.marketplace.aspect;

import com.cosmocats.marketplace.service.FeatureToggleService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    public FeatureToggleAspect(FeatureToggleService featureToggleService) {
        this.featureToggleService = featureToggleService;
    }

    @Before("@annotation(checkFeature)")
    public void checkFeatureEnabled(CheckFeature checkFeature) {
        String featureName = checkFeature.value();

        if (!featureToggleService.isEnabled(featureName)) {
            throw new RuntimeException("Feature '" + featureName + "' is disabled");
        }
    }
}