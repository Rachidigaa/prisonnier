package com.example.demo.core.domain.strategies;


import com.example.demo.core.domain.enums.Decision;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class DonnantDonnantAleatoire implements IStrategie {

    // Utilisation de SecureRandom pour une génération sécurisée
    private final Random random = new SecureRandom();
    private double randomProbability = 0.1;

    @Override
    public Decision decider(List<Decision> opponent) {
        // Générer une décision aléatoire avec une probabilité définie
        if (random.nextDouble() < randomProbability) {
            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
        }

        // Revenir au dernier coup de l'adversaire si aucune décision aléatoire n'est prise
        return opponent.get(opponent.size() - 1);

    }
}
