package fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class DonnantDonnantAleatoire implements IStrategie {

   
    private final Random random = new SecureRandom();
    private double randomProbability = 0.1;

    @Override
    public Decision decider(List<Decision> opponent) {
       
        if (random.nextDouble() < randomProbability) {
            return random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
        }

       
        return opponent.get(opponent.size() - 1);

    }
}
