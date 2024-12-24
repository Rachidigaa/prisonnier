package fr.uga.miage.m1.my_projet_g1_10.service;

import fr.uga.miage.m1.my_projet_g1_10.Repository.GameRepository;
import fr.uga.miage.m1.my_projet_g1_10.Repository.PlayerRepository;
import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameServiceTest {

    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameRepository = mock(GameRepository.class);
        playerRepository = mock(PlayerRepository.class);
        gameService = new GameService(gameRepository, playerRepository);
    }

    @Test
    void testCreateGame() {
        when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Game game = gameService.createGame("Player1", 10);

        assertNotNull(game);
        assertEquals(10, game.getRounds());
        assertEquals(1, game.getPlayers().size());
        assertEquals("Player1", game.getPlayers().get(0).getName());

        verify(gameRepository, times(1)).save(game);
    }

    @Test
    void testJoinGame() {
        Game game = new Game();
        game.setRounds(10);
        game.setPlayers(new ArrayList<>());

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Game> result = gameService.joinGame(1L, "Player2");

        assertTrue(result.isPresent());
        assertEquals(1, game.getPlayers().size());
        assertEquals("Player2", game.getPlayers().get(0).getName());
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void testJoinGame_GameNotFound() {
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Game> result = gameService.joinGame(1L, "Player2");

        assertFalse(result.isPresent());
    }

    @Test
    void testPlayRound_GameNotFound() {
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        var result = gameService.playRound(1L, 1L, "COOPERER");

        assertEquals("Game not found.", result.get("message"));
    }

    @Test
    void testPlayRound_PlayerNotFound() {
        Game game = new Game();
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        var result = gameService.playRound(1L, 1L, "COOPERER");

        assertEquals("Player not found.", result.get("message"));
    }

    @Test
    void testPlayRound_Success() {
        Game game = new Game();
        game.setRounds(10);
        game.setCurrentRound(1);
        game.setPlayers(new ArrayList<>());

        Player player1 = new Player();
        player1.setId(1L);
        player1.setName("Player1");
        game.getPlayers().add(player1);

        Player player2 = new Player();
        player2.setId(2L);
        player2.setName("Player2");
        game.getPlayers().add(player2);

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player1));
        when(playerRepository.findById(2L)).thenReturn(Optional.of(player2));

        var result = gameService.playRound(1L, 1L, "COOPERER");

        assertEquals("Waiting for the other player to submit their move.", result.get("message"));
    }

    @Test
    void testQuitterPartie_PlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        String result = gameService.quitterPartie(1L, "ALEATOIRE");

        assertEquals("Player not found", result);
    }

    @Test
    void testQuitterPartie_Success() {
        Player player = new Player();
        player.setId(1L);
        player.setName("Player1");
        player.setPresent(true);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        String result = gameService.quitterPartie(1L, "ALEATOIRE");

        assertEquals("Joueur Player1 a quitté la partie avec la stratégie ALEATOIRE", result);
        assertFalse(player.getPresent());
    }

    @Test
    void testGetGameScores() {
        Game game = new Game();

        Player player1 = new Player();
        player1.setName("Player1");
        player1.incrementScore(5);

        Player player2 = new Player();
        player2.setName("Player2");
        player2.incrementScore(3);

        game.setPlayers(List.of(player1, player2));

        var scores = gameService.getGameScores(game);

        assertEquals(2, scores.size());
        assertEquals(5, scores.get("Player1"));
        assertEquals(3, scores.get("Player2"));
    }

    @Test
    void testCompareMovesAndCalculateScore() {
        Player player1 = new Player();
        Player player2 = new Player();

        player1.setCurrentMove(Decision.COOPERER);
        player2.setCurrentMove(Decision.COOPERER);

        String result = gameService.compareMovesAndCalculateScore(player1, player2);

        assertEquals("Both players cooperated. Both get 3 points.", result);
        assertEquals(3, player1.getScore());
        assertEquals(3, player2.getScore());
    }

    @Test
    void testProcessRoundEnd() {
        Game game = new Game();
        game.setRounds(2);
        game.setCurrentRound(1);

        Player player1 = new Player();
        Player player2 = new Player();

        player1.setCurrentMove(Decision.COOPERER);
        player2.setCurrentMove(Decision.TRAHIR);

        game.setPlayers(List.of(player1, player2));

        when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = gameService.processRoundEnd(game, player1, player2);

        assertEquals("Game finished! null cooperated, null betrayed. null gets 5 points.", result);
        assertEquals(0, player1.getScore());
        assertEquals(5, player2.getScore());
        assertEquals(2, game.getCurrentRound());
    }

    @Test
    void testSubmitOtherPlayerMove() {
        Player player = new Player();
        player.setStrategie(Strategie.ALEATOIRE);
        player.setMoveHistory(List.of(Decision.COOPERER));

        when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> invocation.getArgument(0));

        gameService.submitOtherPlayerMove(player);

        assertNotNull(player.getCurrentMove());
    }

    @Test
    void testGenerateAndSubmitMove() {
        Player player = new Player();
        player.setStrategie(Strategie.ALEATOIRE);

        when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> invocation.getArgument(0));

        gameService.generateAndSubmitMove(player);

        assertNotNull(player.getCurrentMove());
    }

    @Test
    void testGetGameStatus() {
        Game game = new Game();
        game.setRounds(3);
        game.setCurrentRound(2);
        game.setStatus("STARTED");

        Player player1 = new Player();
        player1.setId(1L);
        player1.setName("Player1");
        player1.incrementScore(3);

        Player player2 = new Player();
        player2.setId(2L);
        player2.setName("Player2");
        player2.incrementScore(5);

        game.setPlayers(List.of(player1, player2));

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        Map<String, Object> status = gameService.getGameStatus(1L);

        assertNotNull(status);
        assertEquals("STARTED", status.get("status"));
        assertEquals(2, status.get("currentRound"));
        assertEquals(3, status.get("rounds"));
        assertEquals(2, ((List<?>) status.get("players")).size());
    }
}
