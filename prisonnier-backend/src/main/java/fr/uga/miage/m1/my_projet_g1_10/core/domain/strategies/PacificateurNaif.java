package com.example.demo.core.domain.strategies;


import com.example.demo.core.domain.enums.Decision;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class PacificateurNaif implements IStrategie {
    // Utilisation de SecureRandom pour des raisons de sécurité
    private final Random random = new SecureRandom();
    private double probabiliteDeTrahir = 0.1;

    public void setProbabiliteDeTrahir(Double probabilite) {
        this.probabiliteDeTrahir = probabilite;
    }

    @Override
    public Decision decider(List<Decision> opponent) {
        // Générer une décision aléatoire pour trahir avec une petite probabilité
        if (random.nextDouble() < probabiliteDeTrahir) {
            return Decision.TRAHIR;
        }
        return Decision.COOPERER;
    }
}
