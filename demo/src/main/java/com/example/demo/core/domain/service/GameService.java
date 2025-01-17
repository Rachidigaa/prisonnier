package fr.uga.miage.m1.my_projet_g1_10.core.domain.service;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.core.usecases.GameServicePort;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Player;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Round;
import fr.uga.miage.m1.my_projet_g1_10.core.repositories.GameRepository;
import fr.uga.miage.m1.my_projet_g1_10.core.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GameService implements GameServicePort {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public GameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Game createGame(String playerName, int rounds) {
        Game game = new Game();
        game.setRounds(rounds);

        Player player = new Player();
        player.setName(playerName);
        game.addPlayer(player);

        gameRepository.save(game);
        return game;
    }

    @Override
    public Optional<Game> joinGame(Long gameId, String playerName) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            if (!game.isFull() && !game.isFinished()) {
                Player player = new Player();
                player.setName(playerName);
                game.addPlayer(player);

                if (game.isFull()) {
                    game.setStatus("STARTED");
                }

                gameRepository.save(game);
                return Optional.of(game);
            }
        }
        return Optional.empty();
    }

    @Override
    public Map<String, Object> playRound(Long gameId, Long playerId, Decision move) {
        Map<String, Object> response = new HashMap<>();

        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isEmpty()) {
            response.put("message", "Game not found.");
            return response;
        }

        Game game = optionalGame.get();

        if (game.isFinished()) {
            response.put("message", "Game is already finished.");
            response.put("scores", getScores(game));
            return response;
        }

        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        if (optionalPlayer.isEmpty()) {
            response.put("message", "Player not found.");
            return response;
        }

        Player currentPlayer = optionalPlayer.get();
        currentPlayer.setCurrentMove(move);
        currentPlayer.addMove(move);
        playerRepository.save(currentPlayer);

        Player otherPlayer = game.getPlayers().stream()
                .filter(p -> !p.getId().equals(playerId))
                .findFirst()
                .orElse(null);

        if (otherPlayer != null && otherPlayer.getCurrentMove() != null) {
            String result = calculateRoundResult(currentPlayer, otherPlayer);

            // Create a map of player moves
            Map<Long, Decision> playerMoves = new HashMap<>();
            playerMoves.put(currentPlayer.getId(), currentPlayer.getCurrentMove());
            playerMoves.put(otherPlayer.getId(), otherPlayer.getCurrentMove());

            // Create a new Round object and add it to the round history
            Round round = new Round(
                    game.getCurrentRound() + 1, // Round number (currentRound is 0-based)
                    playerMoves,
                    result
            );
            game.addRound(round);

            resetMoves(currentPlayer, otherPlayer);
            game.incrementRound();
            gameRepository.save(game);

            response.put("message", result);
            response.put("scores", getScores(game));
            return response;
        }

        response.put("message", "Waiting for the other player to submit their move.");
        response.put("scores", getScores(game));
        return response;
    }

    @Override
    public Map<String, Object> getGameStatus(Long gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            Map<String, Object> response = new HashMap<>();
            response.put("status", game.getStatus());
            response.put("currentRound", game.getCurrentRound());
            response.put("rounds", game.getRounds());

            // Deduce lastMove and score from roundHistory
            Map<Long, Decision> lastMoves = new HashMap<>(); // Player ID -> Last Move
            Map<Long, Integer> scores = new HashMap<>(); // Player ID -> Score

            // Initialize scores for all players
            for (Player player : game.getPlayers()) {
                scores.put(player.getId(), 0); // Start with a score of 0
            }

            // Iterate through roundHistory to calculate lastMove and score
            for (Round round : game.getRoundHistory()) {
                // Update lastMove for each player
                for (Map.Entry<Long, Decision> entry : round.getPlayerMoves().entrySet()) {
                    lastMoves.put(entry.getKey(), entry.getValue());
                }

                // Calculate scores for the round using calculateRoundResult
                Player player1 = game.getPlayers().stream()
                        .filter(p -> p.getId().equals(round.getPlayerMoves().keySet().iterator().next()))
                        .findFirst()
                        .orElse(null);
                Player player2 = game.getPlayers().stream()
                        .filter(p -> !p.getId().equals(player1.getId()))
                        .findFirst()
                        .orElse(null);

                if (player1 != null && player2 != null) {
                    // Temporarily set the current moves for scoring
                    player1.setCurrentMove(round.getPlayerMoves().get(player1.getId()));
                    player2.setCurrentMove(round.getPlayerMoves().get(player2.getId()));

                    // Calculate the result and update scores
                    calculateRoundResult(player1, player2);

                    // Update the scores map
                    scores.put(player1.getId(), player1.getScore());
                    scores.put(player2.getId(), player2.getScore());

                    // Reset the current moves
                    player1.resetMove();
                    player2.resetMove();
                }
            }

            // Build the players' data
            response.put("players", game.getPlayers().stream().map(player -> {
                Map<String, Object> playerData = new HashMap<>();
                playerData.put("id", player.getId());
                playerData.put("name", player.getName());
                playerData.put("score", scores.get(player.getId())); // Deduce score from roundHistory
                playerData.put("hasPlayed", player.hasPlayed());

                // Deduce lastMove from roundHistory
                Decision lastMove = lastMoves.get(player.getId());
                playerData.put("lastMove", lastMove != null ? lastMove : null);

                return playerData;
            }).toList());

            // Add round history to the response
            response.put("roundHistory", game.getRoundHistory());

            return response;
        }
        return Map.of("message", "Game not found.");
    }

    @Override
    public Map<String, Object> getGameScore(Long gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            Map<String, Object> response = new HashMap<>();
            response.put("scores", getScores(game));
            return response;
        }
        return Map.of("message", "Game not found.");
    }

    @Override
    public String quitGame(Long playerId, String strategie) {
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            player.setPresent(false);
            player.setStrategie(Strategie.valueOf(strategie));
            playerRepository.save(player);
            Decision move = fr.uga.miage.m1.my_projet_g1_10.core.domain.strategiesCreators.StrategieFactory.getStrategie(player.getStrategie()).decider(player.getMoveHistory());
            player.setCurrentMove(move);
            playerRepository.save(player);

            return "Player " + player.getName() + " has quit the game with strategy " + strategie + ".";
        }
        return "Player not found.";
    }

    @Override
    public Strategie[] getStrategies() {
        return Strategie.values();
    }

    private String calculateRoundResult(Player player1, Player player2) {
        Decision move1 = player1.getCurrentMove();
        Decision move2 = player2.getCurrentMove();

        if (move1 == Decision.TRAHIR && move2 == Decision.TRAHIR) {
            player1.incrementScore(1);
            player2.incrementScore(1);
            return "Both players betrayed. Both get 1 point.";
        } else if (move1 == Decision.COOPERER && move2 == Decision.COOPERER) {
            player1.incrementScore(3);
            player2.incrementScore(3);
            return "Both players cooperated. Both get 3 points.";
        } else if (move1 == Decision.TRAHIR && move2 == Decision.COOPERER) {
            player1.incrementScore(5);
            player2.incrementScore(0);
            return player1.getName() + " betrayed, " + player2.getName() + " cooperated. " + player1.getName() + " gets 5 points.";
        } else if (move1 == Decision.COOPERER && move2 == Decision.TRAHIR) {
            player1.incrementScore(0);
            player2.incrementScore(5);
            return player1.getName() + " cooperated, " + player2.getName() + " betrayed. " + player2.getName() + " gets 5 points.";
        }
        return "Error comparing moves.";
    }

    private void resetMoves(Player player1, Player player2) {
        player1.resetMove();
        player2.resetMove();
        playerRepository.save(player1);
        playerRepository.save(player2);
    }

    private Map<String, Integer> getScores(Game game) {
        Map<String, Integer> scores = new HashMap<>();
        for (Player player : game.getPlayers()) {
            scores.put(player.getName(), player.getScore());
        }
        return scores;
    }
}