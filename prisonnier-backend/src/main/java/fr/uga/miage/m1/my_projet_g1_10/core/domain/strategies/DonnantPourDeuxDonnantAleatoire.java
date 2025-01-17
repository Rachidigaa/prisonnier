package fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class DonnantPourDeuxDonnantAleatoire implements IStrategie {

   
    private final Random random = new SecureRandom();
    private double randomProbability = 0.1;
    private Decision lastDecision = Decision.COOPERER;

    @Override
    public Decision decider(List<Decision> opponent) {
       
        if (random.nextDouble() < randomProbability) {
            lastDecision = random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
            return lastDecision;
        }

       
        Decision lastMove = opponent.get(opponent.size() - 1);
        Decision secondLastMove = opponent.get(opponent.size() - 2);

       
        if (lastMove == secondLastMove) {
            lastDecision = lastMove;
        }

        return lastDecision;
    }
}
