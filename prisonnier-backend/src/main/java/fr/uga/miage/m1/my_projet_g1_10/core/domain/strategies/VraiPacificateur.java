package fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class VraiPacificateur implements IStrategie {

   
    private final Random random = new SecureRandom();
    private  static final double PEACE_OFFER_PROBABILITY = 0.2; 
    private boolean lastTwoMovesWereBetrayals = false;

    @Override
    public Decision decider(List<Decision> opponent) {
       
        if (opponent.size() >= 2) {
            Decision lastMove = opponent.get(opponent.size() - 1);
            Decision secondLastMove = opponent.get(opponent.size() - 2);

            lastTwoMovesWereBetrayals = (lastMove == Decision.TRAHIR && secondLastMove == Decision.TRAHIR);
        }

       
        if (lastTwoMovesWereBetrayals) {
           
            if (random.nextDouble() < PEACE_OFFER_PROBABILITY) {
                lastTwoMovesWereBetrayals = false; 
                return Decision.COOPERER;
            }
            return Decision.TRAHIR;
        }

       
        return Decision.COOPERER;
    }
}
