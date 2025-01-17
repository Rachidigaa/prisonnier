package fr.uga.miage.m1.my_projet_g1_10.presentation.controller;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Round;
import fr.uga.miage.m1.my_projet_g1_10.core.usecase.GameControllerPort;
import fr.uga.miage.m1.my_projet_g1_10.core.usecase.GameServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class GameController implements GameControllerPort {

    private final GameServicePort gameService;

    public GameController(GameServicePort gameService) {
        this.gameService = gameService;
    }

    @Override
    public ResponseEntity<Game> createGame(@RequestParam int totalRounds) {
        if (totalRounds <= 0) {
            throw new IllegalArgumentException("Total rounds must be greater than zero.");
        }
        Game game = gameService.createGame(totalRounds);
        return ResponseEntity.ok(game);
    }

    @Override
    public ResponseEntity<Map<String, Object>> joinGame(@RequestParam Long gameId, @RequestParam String username) {
        Map<String, Object> response = gameService.joinGame(gameId, username);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Round> playRound(@RequestParam Long gameId, @RequestParam Long playerId, @RequestParam Decision decision) {
        Round round = gameService.playRound(gameId, playerId, decision)
                .orElseThrow(() -> new IllegalArgumentException("Invalid game or player."));
        return ResponseEntity.ok(round);
    }

    @Override
    public ResponseEntity<Void> abandonGame(@RequestParam Long gameId, @RequestParam Long playerId, @RequestParam Strategie strategy) {
        gameService.abandonGame(gameId, playerId, strategy);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Game> getGameStatus(
            @PathVariable Long gameId,
            @RequestParam Long requestingPlayerId) {

        Game game = gameService.getGameStatus(gameId, requestingPlayerId);
        return ResponseEntity.ok(game);
    }
    @Override
    public ResponseEntity<List<Strategie>> getStrategies() {
        return ResponseEntity.ok(Arrays.asList(Strategie.values()));
    }
}
