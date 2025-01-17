package fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class PavlovAleatoire implements IStrategie {

   
    private final Random random = new SecureRandom();
    private Decision lastDecision = Decision.COOPERER;
    private int lastScore = 0;

    public void setLastScore(int score) {
        this.lastScore = score;
    }

    public Decision getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(Decision lastDecision) {
        this.lastDecision = lastDecision;
    }

    @Override
    public Decision decider(List<Decision> opponent) {
       
        if (random.nextDouble() < 0.1) {
            lastDecision = random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
            return lastDecision;
        }

       
        if (lastScore == 5 || lastScore == 3) {
            return lastDecision;
        } else {
           
            lastDecision = (lastDecision == Decision.COOPERER) ? Decision.TRAHIR : Decision.COOPERER;
            return lastDecision;
        }
    }
}
