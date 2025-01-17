package fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;

import java.util.List;

public class ToujoursTrahir implements IStrategie {
    @Override
    public Decision decider(List<Decision> opponent) {
        return Decision.TRAHIR;
    }
}
