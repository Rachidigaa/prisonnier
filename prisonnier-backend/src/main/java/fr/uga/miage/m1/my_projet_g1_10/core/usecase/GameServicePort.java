package fr.uga.miage.m1.my_projet_g1_10.core.usecase;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Round;

import java.util.Map;
import java.util.Optional;

public interface GameServicePort {

    Game createGame(int totalRounds);

    Map<String, Object> joinGame(Long gameId, String username);

    Optional<Round> playRound(Long gameId, Long playerId, Decision decision);

    void abandonGame(Long gameId, Long playerId, Strategie strategy);

    Game getGameStatus(Long gameId,Long requestingPlayerId);
}