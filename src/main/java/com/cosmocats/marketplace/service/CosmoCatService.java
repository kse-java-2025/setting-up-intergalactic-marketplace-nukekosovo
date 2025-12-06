package com.cosmocats.marketplace.service;

import com.cosmocats.marketplace.aspect.CheckFeature;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CosmoCatService {

    // dummy function just to prove the point
    @CheckFeature("cosmoCats")
    public List<String> getCosmoCats() {
        return List.of("Space Garfield", "Nebula Nyan", "Galactic Grumpy Cat");
    }
}