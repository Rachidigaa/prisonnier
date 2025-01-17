package fr.uga.miage.m1.my_projet_g1_10.core.usecase;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Round;
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
