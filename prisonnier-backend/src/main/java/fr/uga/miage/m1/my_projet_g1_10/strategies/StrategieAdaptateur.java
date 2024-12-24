package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.g2_12.enums.TribeAction;
import fr.uga.miage.g2_12.strategies.Strategy;
import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;

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
