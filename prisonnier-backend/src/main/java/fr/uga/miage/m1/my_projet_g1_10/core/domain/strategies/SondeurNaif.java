package com.example.demo.core.domain.strategies;


import com.example.demo.core.domain.enums.Decision;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class SondeurNaif implements IStrategie {

    // Utilisation de SecureRandom pour une génération sécurisée et réutilisation de l'instance
    private final Random random = new SecureRandom();
    private double probabiliteDeTrahir = 0.1;

    @Override
    public Decision decider(List<Decision> opponent) {
        // Si la liste est vide, coopérer par défaut
        if (opponent.isEmpty()) {
            return Decision.COOPERER;
        }

        // Avec une petite probabilité, trahir
        if (random.nextDouble() < probabiliteDeTrahir) {
            return Decision.TRAHIR;
        }

        // Sinon, renvoyer la dernière décision de l'adversaire
        return opponent.get(opponent.size() - 1);
    }
}
