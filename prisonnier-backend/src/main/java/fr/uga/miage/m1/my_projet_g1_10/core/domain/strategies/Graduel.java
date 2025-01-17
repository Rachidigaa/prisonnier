package fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;

import java.util.List;

public class Graduel implements IStrategie {

    private int betrayCount = 0;
    private int remainingBetrayals = 0;
    private int cooperativePhase = 0; 

    @Override
    public Decision decider(List<Decision> opponent) {
        if (opponent.isEmpty()) {
            return Decision.COOPERER; 
        }

        Decision lastOpponentMove = opponent.get(opponent.size() - 1);

       
        if (lastOpponentMove == Decision.TRAHIR) {
            betrayCount++;
            remainingBetrayals = betrayCount; 
            cooperativePhase = 0; 
        }

       
        if (remainingBetrayals > 0) {
            remainingBetrayals--;
            return Decision.TRAHIR;
        }

       
        if (cooperativePhase < 2) {
            cooperativePhase++;
            return Decision.COOPERER;
        }

       
        return Decision.COOPERER;
    }
}