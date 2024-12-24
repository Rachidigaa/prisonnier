package fr.uga.miage.m1.my_projet_g1_10.model;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.enums.Strategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
        player.setName("John Doe");
        player.setScore(0);
        player.setStrategie(Strategie.TOUJOURSCOOPERER);
        player.setPresent(true);
    }

    @Test
    void testGetAndSetName() {
        player.setName("Alice");
        assertEquals("Alice", player.getName());
    }

    @Test
    void testIncrementScore() {
        player.incrementScore(10);
        assertEquals(10, player.getScore());

        player.incrementScore(5);
        assertEquals(15, player.getScore());
    }

    @Test
    void testSetAndGetScore() {
        player.setScore(20);
        assertEquals(20, player.getScore());
    }

    @Test
    void testSetAndGetCurrentMove() {
        player.setCurrentMove(Decision.COOPERER);
        assertEquals(Decision.COOPERER, player.getCurrentMove());

        player.setCurrentMove(Decision.TRAHIR);
        assertEquals(Decision.TRAHIR, player.getCurrentMove());
    }

    @Test
    void testMoveHistory() {
        player.addMoves(Decision.COOPERER);
        player.addMoves(Decision.TRAHIR);

        List<Decision> moveHistory = player.getMoveHistory();
        assertEquals(2, moveHistory.size());
        assertEquals(Decision.COOPERER, moveHistory.get(0));
        assertEquals(Decision.TRAHIR, moveHistory.get(1));
    }

    @Test
    void testGetLastMove() {
        assertNull(player.getLastMove()); // Vérifie que la liste est vide au départ

        player.addMoves(Decision.COOPERER);
        assertEquals(Decision.COOPERER, player.getLastMove());

        player.addMoves(Decision.TRAHIR);
        assertEquals(Decision.TRAHIR, player.getLastMove());
    }

    @Test
    void testSetAndGetStrategie() {
        player.setStrategie(Strategie.DONNANTDONNANT);
        assertEquals(Strategie.DONNANTDONNANT, player.getStrategie());
    }

    @Test
    void testChangePresent() {
        assertTrue(player.getPresent());

        player.changePresent();
        assertFalse(player.getPresent());

        player.changePresent();
        assertTrue(player.getPresent());
    }

    @Test
    void testSetAndGetGame() {
        Game game = new Game();
        game.setId(1L);

        player.setGame(game);
        assertNotNull(player.getGame());
        assertEquals(1L, player.getGame().getId());
    }
}

