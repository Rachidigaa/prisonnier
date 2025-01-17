package com.example.demo.core.domain.strategies;

import com.example.demo.core.domain.enums.Decision;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
public class Aleatoire implements IStrategie {

    // Utilisation de SecureRandom pour des valeurs aléatoires sécurisées
    private final Random random = new SecureRandom();

    @Override
    public Decision decider(List<Decision> opponent) {
        int randomNumber = random.nextInt(2);
        if (randomNumber == 0)
            return Decision.TRAHIR;
        else
            return Decision.COOPERER;
    }
}