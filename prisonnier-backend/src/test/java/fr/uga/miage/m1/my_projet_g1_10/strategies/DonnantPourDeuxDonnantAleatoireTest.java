package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies.DonnantPourDeuxDonnantAleatoire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DonnantPourDeuxDonnantAleatoireTest {

    private DonnantPourDeuxDonnantAleatoire strategy;

    @BeforeEach
    public void setUp() {
        strategy = new DonnantPourDeuxDonnantAleatoire();
    }

    @Test
    void testRandomDecisionAfterTwoSameMoves() {
        List<Decision> opponentMoves = new ArrayList<>();
        opponentMoves.add(Decision.COOPERER);
        opponentMoves.add(Decision.COOPERER);
        Decision decision = strategy.decider(opponentMoves);
        assertTrue(decision == Decision.COOPERER || decision == Decision.TRAHIR);
    }
}
