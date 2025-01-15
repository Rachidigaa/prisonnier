package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies.SondeurRepentant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

class SondeurRepentantTest {

    private SondeurRepentant strategy;
    private List<Decision> opponentMoves;

    @BeforeEach
    void setUp() {
        strategy = new SondeurRepentant();
        opponentMoves = new ArrayList<>();
    }

    @Test
    void testInitialCooperation() {
        // Si l'adversaire n'a pas encore joué, le premier coup doit être la coopération
        Decision decision = strategy.decider(opponentMoves);
        assertEquals(Decision.COOPERER, decision, "Le premier coup doit être la coopération");
    }

    @Test
    void testStayInCooperationModeAfterOpponentCooperation() {
        // L'adversaire coopère, donc le joueur doit imiter et coopérer également
        opponentMoves.add(Decision.COOPERER);
        Decision decision = strategy.decider(opponentMoves);
        assertEquals(Decision.COOPERER, decision, "Le joueur doit coopérer après une coopération de l'adversaire");
    }

    @Test
    void testImitationOfLastMove() {
        // Si l'adversaire a trahi en dernier et qu'il n'y a pas de test en cours, le joueur doit imiter
        opponentMoves.add(Decision.TRAHIR);
        Decision decision = strategy.decider(opponentMoves);
        assertEquals(Decision.TRAHIR, decision, "Le joueur doit imiter la dernière décision de l'adversaire");
    }
}
