package com.example.demo.core.usecase;


import com.example.demo.core.domain.enums.Decision;
import com.example.demo.core.domain.enums.Strategie;
import com.example.demo.core.domain.model.Game;
import com.example.demo.core.domain.model.Round;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface GameServicePort {

    Game createGame(int totalRounds);

    Map<String, Object> joinGame(Long gameId, String username);

    Optional<Round> playRound(Long gameId, Long playerId, Decision decision);

    void abandonGame(Long gameId, Long playerId, Strategie strategy);

    Game getGameStatus(Long gameId,Long requestingPlayerId);
}