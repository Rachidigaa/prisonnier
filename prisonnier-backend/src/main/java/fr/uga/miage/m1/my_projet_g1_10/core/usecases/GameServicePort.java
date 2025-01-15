package fr.uga.miage.m1.my_projet_g1_10.core.usecases;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Player;

import java.util.Map;
import java.util.Optional;

public interface GameServicePort {
    Game createGame(String playerName, int rounds);
    Optional<Game> joinGame(Long gameId, String playerName);
    Map<String, Object> playRound(Long gameId, Long playerId, String playerMove);
    Map<String, Integer> getGameScores(Game game);
    void submitPlayerMove(Player player, Decision decision);
    Player findOtherPlayer(Game game, Long playerId);
    void submitOtherPlayerMove(Player otherPlayer);
    String processRoundEnd(Game game, Player currentPlayer, Player otherPlayer);
    void resetMoves(Player currentPlayer, Player otherPlayer);
    Map<String, Object> getGameStatus(Long gameId);
    String compareMovesAndCalculateScore(Player player1, Player player2);
    String quitterPartie(Long playerId, String strategiePlayer);
    Decision generateMove(Player player);
    void generateAndSubmitMove(Player player);
    Map<String, Object> getGameScore(Long gameId);

}
