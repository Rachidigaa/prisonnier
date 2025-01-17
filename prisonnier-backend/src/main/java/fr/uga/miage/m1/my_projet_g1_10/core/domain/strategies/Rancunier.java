package com.example.demo.core.domain.strategies;


import com.example.demo.core.domain.enums.Decision;

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