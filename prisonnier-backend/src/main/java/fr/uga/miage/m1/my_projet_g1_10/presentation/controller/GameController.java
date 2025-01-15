package fr.uga.miage.m1.my_projet_g1_10.presentation.controller;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.service.GameService;
import fr.uga.miage.m1.my_projet_g1_10.core.usecases.GameControllerPort;
import fr.uga.miage.m1.my_projet_g1_10.core.usecases.GameServicePort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = "http://frontend:4200")
@RestController
public class GameController implements GameControllerPort {

    @Qualifier("gameService")
    private final GameServicePort gameServicePort;

    public GameController(GameService gameServicePort) {
        this.gameServicePort = gameServicePort;
    }



    public Game createGame(String playerName, int rounds) {
        return gameServicePort.createGame(playerName, rounds);
    }



    public ResponseEntity<Game> joinGame(Long gameId, String playerName) {
        Optional<Game> game = gameServicePort.joinGame(gameId, playerName);
        return game.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }


    // incrémenter le tour et vérifier si la partie est terminée

    public ResponseEntity<Map<String, Object>> playRound(
            Long gameId,
            Long playerId,
            String move
    ) {
        Map<String, Object> response = gameServicePort.playRound(gameId, playerId, move);
        return ResponseEntity.ok(response);
    }





    public ResponseEntity<Map<String, String>> quittePartie(
            Long playerId,
            String strategie
    ) {
        String result = gameServicePort.quitterPartie(playerId, strategie);
        Map<String, String> response = new HashMap<>();
        response.put("message", result);
        return ResponseEntity.ok(response);
    }



    public ResponseEntity<Map<String, Object>> getGameStatus(Long gameId) {
        Map<String, Object> gameStatus = gameServicePort.getGameStatus(gameId);
        if (gameStatus != null) {
            return ResponseEntity.ok(gameStatus);
        }
        return ResponseEntity.notFound().build(); // Retourne 404 si le jeu n'existe pas
    }


    public ResponseEntity<Map<String, Object>> getGameScore(Long gameId) {
        return ResponseEntity.ok(gameServicePort.getGameScore(gameId));
    }


    public ResponseEntity<Strategie[]> getStrategies() {
        return ResponseEntity.ok(Strategie.values());
    }







}
