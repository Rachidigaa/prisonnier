package fr.uga.miage.m1.my_projet_g1_10.model;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(player);
        assertNull(player.getId());
        assertNull(player.getUsername());
        assertEquals(0, player.getScore());
        assertFalse(player.isHasPlayed());
        assertNull(player.getStrategy());
        assertNull(player.getGame());
        assertNull(player.getStrategyImplementation());
    }

    @Test
    void testSetAndGetId() {
        player.setId(1L);
        assertEquals(1L, player.getId());
    }

    @Test
    void testSetAndGetUsername() {
        player.setUsername("TestPlayer");
        assertEquals("TestPlayer", player.getUsername());
    }

    @Test
    void testSetAndGetScore() {
        player.setScore(100);
        assertEquals(100, player.getScore());
    }

    @Test
    void testSetAndIsHasPlayed() {
        player.setHasPlayed(true);
        assertTrue(player.isHasPlayed());

        player.setHasPlayed(false);
        assertFalse(player.isHasPlayed());
    }

    @Test
    void testSetAndGetStrategy() {
        player.setStrategy(Strategie.DONNANTDONNANT);
        assertEquals(Strategie.DONNANTDONNANT, player.getStrategy());
        assertNotNull(player.getStrategyImplementation());
    }

    @Test
    void testSetAndGetGame() {
        Game game = new Game();
        game.setId(1L);
        player.setGame(game);
        assertEquals(game, player.getGame());
        assertEquals(1L, player.getGame().getId());
    }

    @Test
    void testStrategyImplementationIsUpdated() {
        player.setStrategy(Strategie.ALEATOIRE);
        assertNotNull(player.getStrategyImplementation());

        player.setStrategy(Strategie.DONNANTDONNANT);
        assertNotNull(player.getStrategyImplementation());
        assertEquals(Strategie.DONNANTDONNANT, player.getStrategy());
    }

    @Test
    void testGameReferenceInPlayer() {
        Game game = new Game();
        game.setId(2L);

        player.setGame(game);
        assertEquals(game, player.getGame());
        assertEquals(2L, player.getGame().getId());
    }
}
