package com.example.demo.core.domain.strategies;


import com.example.demo.core.domain.enums.Decision;
import fr.uga.miage.g2_12.enums.TribeAction;
import fr.uga.miage.g2_12.strategies.Strategy;

import java.util.List;

public class StrategieAdaptateur implements IStrategie {

    private Strategy strategy;

    public StrategieAdaptateur(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public Decision decider(List<Decision> opponentDecisions) {

        TribeAction action = strategy.calculateAction();

        return convertTribeActionToDecision(action);
    }


    // Méthode pour convertir une TribeAction vers Decision
    private Decision convertTribeActionToDecision(TribeAction action) {
        return switch (action) {
            case COOPERATE -> Decision.COOPERER;
            case BETRAY -> Decision.TRAHIR;
            case LEAVE -> Decision.PARTIR;
        };
    }
}
