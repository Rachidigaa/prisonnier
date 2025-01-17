package fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;

import java.util.List;

public class Rancunier implements IStrategie {
    private boolean adversaireATrahi = false;

    @Override
    public Decision decider(List<Decision> opponent) {
        if (!adversaireATrahi && opponent.contains(Decision.TRAHIR)) {
            adversaireATrahi = true;
        }
        if (adversaireATrahi) {
            return Decision.TRAHIR;
        }
        return Decision.COOPERER;
    }
}