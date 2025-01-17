package fr.uga.miage.m1.my_projet_g1_10.core.usecases;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;
import java.util.Map;
import java.util.Optional;

public interface GameServicePort {
    Game createGame(String playerName, int rounds);
    Optional<Game> joinGame(Long gameId, String playerName);
    Map<String, Object> playRound(Long gameId, Long playerId, Decision move);
    Map<String, Object> getGameStatus(Long gameId);
    Map<String, Object> getGameScore(Long gameId);
    String quitGame(Long playerId, String strategie);
    Strategie[] getStrategies();
}