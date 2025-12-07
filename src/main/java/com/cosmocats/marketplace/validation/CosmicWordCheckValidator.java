package com.cosmocats.marketplace.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class CosmicWordCheckValidator implements ConstraintValidator<CosmicWordCheck, String> {

    private static final List<String> COSMIC_TERMS = List.of("star", "galaxy", "cosmic", "comet", "space", "moon", "sun", "planet", "alien");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        String valueLower = value.toLowerCase();

        return COSMIC_TERMS.stream().anyMatch(valueLower::contains);
    }
}