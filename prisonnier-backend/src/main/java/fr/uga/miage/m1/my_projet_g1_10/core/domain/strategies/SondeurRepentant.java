package fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class SondeurRepentant implements IStrategie {

   
    private final Random random = new SecureRandom();
    private double probabiliteDeTrahir = 0.1;
    private boolean lastMoveWasTest = false;

    public Random getRandom() {
        return random;
    }

    @Override
    public Decision decider(List<Decision> opponent) {
       
        if (opponent.isEmpty()) {
            return Decision.COOPERER;
        }

        Decision lastMove = opponent.get(opponent.size() - 1);

       
        if (lastMoveWasTest) {
            if (lastMove == Decision.TRAHIR) {
                lastMoveWasTest = false;
                return Decision.COOPERER;
            }
            lastMoveWasTest = false;
        }

       
        if (random.nextDouble() < probabiliteDeTrahir) {
            lastMoveWasTest = true;
            return Decision.TRAHIR;
        }

       
        return lastMove;
    }
}
