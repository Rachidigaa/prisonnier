package com.example.demo.core.usecase;

import com.example.demo.core.domain.enums.Decision;
import com.example.demo.core.domain.enums.Strategie;
import com.example.demo.core.domain.model.Game;
import com.example.demo.core.domain.model.Round;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/game")
public interface GameControllerPort {
    @PostMapping("/create")
    ResponseEntity<Game> createGame(@RequestParam int totalRounds);

    @PostMapping("/join")
    ResponseEntity<Map<String, Object>> joinGame(@RequestParam Long gameId, @RequestParam String username);

    @PostMapping("/play")
    ResponseEntity<Round> playRound(@RequestParam Long gameId, @RequestParam Long playerId, @RequestParam Decision decision);

    @PostMapping("/abandon")
    ResponseEntity<Void> abandonGame(@RequestParam Long gameId, @RequestParam Long playerId, @RequestParam Strategie strategy);

    @GetMapping("/{gameId}/status")
    ResponseEntity<Game> getGameStatus(@PathVariable Long gameId, Long requestingPlayerId);

    @GetMapping("/strategies")
    ResponseEntity<List<Strategie>> getStrategies();
}
