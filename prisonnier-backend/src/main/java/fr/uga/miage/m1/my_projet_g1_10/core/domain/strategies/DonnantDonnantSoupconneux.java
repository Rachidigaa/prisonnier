package com.example.demo.core.domain.strategies;


import com.example.demo.core.domain.enums.Decision;

import java.util.List;

public class DonnantDonnantSoupconneux implements IStrategie {

    @Override
    public Decision decider(List<Decision> opponent) {
        if (opponent.isEmpty()) {
            return Decision.TRAHIR;
        }

        return opponent.get(opponent.size() - 1);
    }
}
