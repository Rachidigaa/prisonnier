package fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;

import java.util.List;

public class RancunierDoux implements IStrategie {

    private boolean punishmentMode = false;
    private int punishmentStep = 0;

    @Override
    public Decision decider(List<Decision> opponent) {
        if (opponent.isEmpty()) {
            return Decision.COOPERER;
        }

        Decision lastOpponentMove = opponent.get(opponent.size() - 1);

       
        if (lastOpponentMove == Decision.TRAHIR && !punishmentMode) {
            punishmentMode = true;
            punishmentStep = 0; 
        }

       
        if (punishmentMode) {
            Decision currentDecision = (punishmentStep < 4) ? Decision.TRAHIR : Decision.COOPERER;
            punishmentStep++;

           
            if (punishmentStep >= 6) {
                punishmentMode = false;
            }
            return currentDecision;
        }

       
        return Decision.COOPERER;
    }
}
