package com.example.demo.core.domain.strategies;


import com.example.demo.core.domain.enums.Decision;

import java.util.List;

public class DonnantDonnant implements IStrategie {
    @Override
    public Decision decider(List<Decision> opponent){
        if (opponent.isEmpty()) {
            return Decision.COOPERER;
        }
        Decision lastMove = opponent.get(opponent.size() - 1);
        return lastMove.equals(Decision.COOPERER) ? Decision.COOPERER : Decision.TRAHIR;

    }
}
