package fr.uga.miage.m1.my_projet_g1_10.core.domain.service;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.GameState;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Player;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Round;
import fr.uga.miage.m1.my_projet_g1_10.core.repository.GameRepository;
import fr.uga.miage.m1.my_projet_g1_10.core.repository.PlayerRepository;
import fr.uga.miage.m1.my_projet_g1_10.core.repository.RoundRepository;
import fr.uga.miage.m1.my_projet_g1_10.core.usecase.GameServicePort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService implements GameServicePort {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final RoundRepository roundRepository;

    public GameService(@Qualifier("gameRepositoryImp") GameRepository gameRepository,
                       @Qualifier("playerRepositoryImp") PlayerRepository playerRepository,
                       @Qualifier("roundRepositoryImp") RoundRepository roundRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.roundRepository = roundRepository;
    }

    @Override
    public Game createGame(int totalRounds) {
        Game game = new Game();
        game.setTotalRounds(totalRounds);
        game.setState(GameState.WAITING_FOR_PLAYERS);
        return gameRepository.save(game);
    }

    @Override
    public Map<String, Object> joinGame(Long gameId, String username) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            if (game.getPlayers().size() < 2) {
                Player player = new Player();
                player.setUsername(username);
                player.setStrategy(Strategie.ALEATOIRE);
                player.setGame(game);
                game.addPlayer(player);
                Player savedPlayer = playerRepository.save(player);

                if (game.getPlayers().size() == 2) {
                    game.setState(GameState.IN_PROGRESS);
                }
                gameRepository.save(game);

               
                Map<String, Object> response = new HashMap<>();
                response.put("game", game);
                response.put("playerId", savedPlayer.getId());
                return response;
            }
        }
        throw new IllegalArgumentException("Game not found or already full.");
    }


    @Override
    public Optional<Round> playRound(Long gameId, Long playerId, Decision decision) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        if (game.getState() != GameState.IN_PROGRESS) {
            throw new IllegalStateException("Game is not in progress");
        }

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        if (player.isHasPlayed()) {
            throw new IllegalStateException("Player has already made their move");
        }

        Round round;
        if (game.getRounds().size() <= game.getCurrentRound()) {
            round = new Round();
            game.addRound(round);
            round = roundRepository.save(round);
        } else {
            round = game.getRounds().get(game.getCurrentRound());
        }

        round.addDecision(playerId, decision);
        player.setHasPlayed(true);
        playerRepository.save(player);
        Player autoPlayer = game.getPlayers().stream()
                .filter(p -> !p.getId().equals(playerId) && p.isAutoPlayer())
                .findFirst()
                .orElse(null);

        if (autoPlayer != null && !autoPlayer.isHasPlayed()) {
            List<Decision> opponentDecisions = getOpponentDecisions(game, autoPlayer.getId());
            Decision autoDecision = autoPlayer.getStrategyImplementation().decider(opponentDecisions);
            round.addDecision(autoPlayer.getId(), autoDecision);
            autoPlayer.setHasPlayed(true);
            playerRepository.save(autoPlayer);
        }

        if (round.getDecisions().size() == 2) {
            calculateScores(round, game);
            resetPlayersForNextRound(game);
            game.incrementCurrentRound();
            if (game.getCurrentRound() >= game.getTotalRounds()) {
                game.setState(GameState.FINISHED);
            }
        }

        roundRepository.save(round);
        gameRepository.save(game);

        return Optional.of(round);
    }


    @Override
    public void abandonGame(Long gameId, Long playerId, Strategie strategy) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            Optional<Player> optionalPlayer = playerRepository.findById(playerId);
            if (optionalPlayer.isPresent()) {
                Player player = optionalPlayer.get();
                player.setUsername(player.getUsername() + " (auto)");
                player.setStrategy(strategy);
                player.setHasPlayed(false);
                player.setAutoPlayer(true);
                playerRepository.save(player);
                gameRepository.save(game);

            }
        }
    }
    private List<Decision> getOpponentDecisions(Game game, Long autoPlayerId) {
        List<Decision> opponentDecisions = new ArrayList<>();
        for (Round round : game.getRounds()) {
            Map<Long, Decision> decisions = round.getDecisions();
            if (decisions.size() < 2) {
                continue;
            }
            decisions.forEach((playerId, decision) -> {
                if (!playerId.equals(autoPlayerId)) {
                    opponentDecisions.add(decision);
                }
            });
        }
        return opponentDecisions;
    }

    private void resetPlayersForNextRound(Game game) {
        for (Player player : game.getPlayers()) {
            player.setHasPlayed(false);
            playerRepository.save(player);
        }
    }

    private void calculateScores(Round round, Game game) {
       
        List<Long> playerIds = new ArrayList<>(round.getDecisions().keySet());
        if (playerIds.size() != 2) {
            throw new IllegalStateException("There must be exactly two players' decisions to calculate scores");
        }

        Long player1Id = playerIds.get(0);
        Long player2Id = playerIds.get(1);

        Decision player1Decision = round.getDecisions().get(player1Id);
        Decision player2Decision = round.getDecisions().get(player2Id);

        if (player1Decision == Decision.COOPERER && player2Decision == Decision.COOPERER) {
            round.addScore(player1Id, 3);
            round.addScore(player2Id, 3);
        } else if (player1Decision == Decision.COOPERER && player2Decision == Decision.TRAHIR) {
            round.addScore(player1Id, 0);
            round.addScore(player2Id, 5);
        } else if (player1Decision == Decision.TRAHIR && player2Decision == Decision.COOPERER) {
            round.addScore(player1Id, 5);
            round.addScore(player2Id, 0);
        } else if (player1Decision == Decision.TRAHIR && player2Decision == Decision.TRAHIR) {
            round.addScore(player1Id, 1);
            round.addScore(player2Id, 1);
        }

        for (Player player : game.getPlayers()) {
            Long playerId = player.getId();
            if (round.getScores().containsKey(playerId)) {
                int newScore = round.getScores().get(playerId);
                player.setScore(player.getScore() + newScore);
            }
        }
    }

    @Override
    public Game getGameStatus(Long gameId, Long requestingPlayerId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

       
        Game filteredGame = cloneGameWithoutPersistence(game);

       
        if (filteredGame.getCurrentRound() < filteredGame.getRounds().size()) {
            Round currentRound = filteredGame.getRounds().get(filteredGame.getCurrentRound());

           
            if (currentRound.getDecisions().size() < 2) {
                currentRound.getDecisions().forEach((playerId, decision) -> {
                    if (!playerId.equals(requestingPlayerId)) {
                        currentRound.getDecisions().put(playerId, null);
                    }
                });
            }
        }

        return filteredGame;
    }

    private Game cloneGameWithoutPersistence(Game originalGame) {
        Game clonedGame = new Game();
        clonedGame.setId(originalGame.getId());
        clonedGame.setTotalRounds(originalGame.getTotalRounds());
        clonedGame.setCurrentRound(originalGame.getCurrentRound());
        clonedGame.setState(originalGame.getState());

       
        for (Player player : originalGame.getPlayers()) {
            Player clonedPlayer = new Player();
            clonedPlayer.setId(player.getId());
            clonedPlayer.setUsername(player.getUsername());
            clonedPlayer.setScore(player.getScore());
            clonedPlayer.setHasPlayed(player.isHasPlayed());
            clonedGame.addPlayer(clonedPlayer);
        }

       
        for (Round round : originalGame.getRounds()) {
            Round clonedRound = new Round();
            clonedRound.setId(round.getId());
            clonedRound.setDecisions(new HashMap<>(round.getDecisions()));
            clonedRound.setScores(new HashMap<>(round.getScores()));
            clonedGame.addRound(clonedRound);
        }

        return clonedGame;
    }

}
