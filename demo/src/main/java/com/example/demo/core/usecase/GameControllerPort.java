package fr.uga.miage.m1.my_projet_g1_10.core.usecases;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequestMapping("/game")
public interface GameControllerPort {
    @PostMapping("/create")
    Game createGame(@RequestParam String playerName, @RequestParam int rounds);
    @PostMapping("/join")
    ResponseEntity<Game> joinGame(@RequestParam Long gameId, @RequestParam String playerName);
    @PostMapping("/play")
    ResponseEntity<Map<String, Object>> playRound(@RequestParam Long gameId, @RequestParam Long playerId, @RequestParam String move);
    @PostMapping("/quitte")
    ResponseEntity<Map<String, String>> quittePartie(@RequestParam Long playerId, @RequestParam String strategie);
    @GetMapping("/{gameId}/status")
    ResponseEntity<Map<String, Object>> getGameStatus(@PathVariable Long gameId);
    @GetMapping("/{gameId}/score")
    ResponseEntity<Map<String, Object>> getGameScore(@PathVariable Long gameId);
    @GetMapping("/strategies")
    ResponseEntity<Strategie[]> getStrategies();
}
